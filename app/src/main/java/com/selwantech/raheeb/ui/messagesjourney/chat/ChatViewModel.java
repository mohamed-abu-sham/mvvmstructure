package com.selwantech.raheeb.ui.messagesjourney.chat;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChatBinding;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.model.Sender;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiConstants;
import com.selwantech.raheeb.ui.adapter.ChatMessageAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.utils.AudioRecorder;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.SnackViewBulider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatViewModel extends BaseViewModel<ChatNavigator, FragmentChatBinding>
        implements ChatMessageRecyclerClick<ChatObject>, AudioRecorder.RecordCallBack {


    boolean enableLoading = false;
    boolean isLoadMore = false;
    ChatMessageAdapter chatAdapter;
    Socket mSocket;
    int inSideMessageId = 0;
    AudioRecorder audioRecorder;
    private Emitter.Listener onConnect = args ->
            getBaseActivity().runOnUiThread(() -> {
                joinRoom();
            });
    private Emitter.Listener onDisconnect = args ->
            getBaseActivity().runOnUiThread(() -> {
            });
    private Emitter.Listener onConnectError = args ->
            getBaseActivity().runOnUiThread(() -> {
            });
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getBaseActivity().runOnUiThread(() -> {
                if (args != null && args.length > 0) {
                    ChatObject chatObject =
                            new Gson().fromJson(args[0].toString(), ChatObject.class);
                    chatAdapter.addItem(chatObject);
                    notifyAdapter();
                    getViewBinding().recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                }
            });
        }
    };

    public <V extends ViewDataBinding, N extends BaseNavigator> ChatViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ChatNavigator) navigation, (FragmentChatBinding) viewDataBinding);

    }

    private void getData() {
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));
//        chatAdapter.addItem(new ChatObject(8, "text",
//                "Hiiii", "now", new Sender(8, "", "")));

        if (!isLoadMore()) {
            enableLoading = true;
        }
        getDataManager().getMessagesService().getChatMessages(getMyContext(), enableLoading, getNavigator().getChat().getId(), isLoadMore ?
                chatAdapter.getItemCount() : 0, new APICallBack<ArrayList<ChatObject>>() {
            @Override
            public void onSuccess(ArrayList<ChatObject> response) {
                checkIsLoadMoreAndRefreshing(true);
                chatAdapter.addItems(response);
                notifyAdapter();
            }

            @Override
            public void onError(String error, int errorCode) {
                if (!isLoadMore) {
                    showSnackBar(getMyContext().getString(R.string.error),
                            error, getMyContext().getResources().getString(R.string.ok),
                            new SnackViewBulider.SnackbarCallback() {
                                @Override
                                public void onActionClick(Snackbar snackbar) {
                                    snackbar.dismiss();
                                }
                            });
                }
                checkIsLoadMoreAndRefreshing(false);
            }
        });

    }

    @Override
    protected void setUp() {
        try {
            mSocket = IO.socket(ApiConstants.SOCKET_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        audioRecorder = new AudioRecorder(getBaseActivity(), this::callback);
        audioRecorder.checkRecorderPermission();
        setUpSendAction();
        initiateSocket();
        setUpRecycler();
        getViewBinding().edMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    getViewBinding().btnSend.setImageResource(R.drawable.ic_microphone);
                } else {
                    getViewBinding().btnSend.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getData();
    }

    private void setUpSendAction() {
        getViewBinding().btnSend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getViewBinding().edMessage.getText().toString().length() == 0) {
                    audioRecorder.recordAction(event);
                } else {
                    showChat(inSideMessageId, "text", getViewBinding().edMessage.getText().toString());
                    sendTxtMessage(getViewBinding().edMessage.getText().toString());
                    getViewBinding().edMessage.setText("");
                    inSideMessageId = inSideMessageId - 1;
                }
                return false;
            }
        });
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    private void checkIsLoadMoreAndRefreshing(boolean isSuccess) {
        if (isLoadMore()) {
            finishLoadMore();
        } else {
            enableLoading = false;
        }
    }

    public void finishLoadMore() {
        chatAdapter.remove(chatAdapter.getItemCount() - 1);
        notifyAdapter();
        chatAdapter.setLoaded();
        setLoadMore(false);
    }

    private void notifyAdapter() {
        getViewBinding().recyclerView.post(new Runnable() {
            @Override
            public void run() {
                chatAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onSendClicked() {

    }

    private void record() {

    }

    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.VERTICAL, false));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        chatAdapter = new ChatMessageAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(chatAdapter);
        chatAdapter.setOnLoadMoreListener(new ChatMessageAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                chatAdapter.addItem(0, null);
                chatAdapter.notifyItemInserted(0);
                setLoadMore(true);
                getData();
            }
        });
    }

    public void openPickerDialog() {
        PickImageFragmentDialog pickImageFragmentDialog = new PickImageFragmentDialog.Builder().build();
        pickImageFragmentDialog.setMethodCallBack(new PickImageFragmentDialog.methodClick() {
            @Override
            public void onMethodBack(int type) {
                if (type == 1) {
                    PickImageUtility.selectImage(getBaseActivity());
                } else {
                    PickImageUtility.TakePictureIntent(getBaseActivity());
                }
            }
        });
        pickImageFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    public void sendImage(Uri image) {
        showChat(inSideMessageId, "file", image.toString());
//        sendImageMessage(image, "file", inSideMessageId);
        inSideMessageId = inSideMessageId - 1;
    }

    @Override
    public void onClick(ChatObject chatObject, int position, boolean isResend) {
        if (isResend) {
            chatAdapter.getItem(position).setSent(true);
            notifyAdapter();
            sendTxtMessage(chatObject.getMessage());
        }

    }

    private void showChat(int position, String type, String message) {
        ChatObject chatObject = new ChatObject(inSideMessageId, type, message, "now",
                new Sender((int) User.getInstance().getUserID(),
                        User.getInstance().getAvatar(),
                        User.getInstance().getName()));
        if (type.equals("file")) {
            chatObject.setShowProgress(true);
        }
        chatAdapter.addItem(chatObject);
        notifyAdapter();
        getViewBinding().recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);


    }

    protected void sendTxtMessage(String text) {
        if (mSocket.connected()) {
            try {
                JSONObject messageData = new JSONObject();
                messageData.put("created_by", "parent");
                messageData.put("message", text);
                mSocket.emit("send_message", messageData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void initiateSocket() {
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("receive_message", onNewMessage);
        mSocket.connect();
    }

    protected void destroySocket() {
        leaveRoom();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("receive_message", onNewMessage);
    }

    protected void leaveRoom() {
        if (mSocket.connected()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("role", "parent");
                mSocket.emit("leave_room", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.disconnect();
        }
    }

    protected void joinRoom() {
        if (!mSocket.connected()) {
            mSocket.connect();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", "parent");
            mSocket.emit("join_room", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(String recordPath) {

    }
}
