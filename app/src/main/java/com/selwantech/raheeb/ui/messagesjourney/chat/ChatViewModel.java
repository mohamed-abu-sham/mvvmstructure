package com.selwantech.raheeb.ui.messagesjourney.chat;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChatBinding;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.model.Sender;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.chatdata.Chat;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiConstants;
import com.selwantech.raheeb.ui.adapter.ChatMessageAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.AudioPlayerDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.AudioRecorder;
import com.selwantech.raheeb.utils.SnackViewBulider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatViewModel extends BaseViewModel<ChatNavigator, FragmentChatBinding>
        implements ChatMessageRecyclerClick<ChatObject>, AudioRecorder.RecordCallBack {


    boolean enableLoading = false;
    boolean isLoadMore = false;
    boolean canLoadMore = false;
    ChatMessageAdapter chatAdapter;
    Socket mSocket;
    int inSideMessageId = 0;

    MediaPlayer mediaPlayer;

    AudioRecorder audioRecorder;
    Chat chat;


    public <V extends ViewDataBinding, N extends BaseNavigator> ChatViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ChatNavigator) navigation, (FragmentChatBinding) viewDataBinding);

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getBaseActivity().runOnUiThread(() -> {
                if (args != null && args.length > 0) {
                    ChatObject chatObject =
                            new Gson().fromJson(args[0].toString(), ChatObject.class);
                    if (chatObject.getSender().getId() != User.getInstance().getUserID()) {
                        chatAdapter.addItem(chatObject);
                        chatAdapter.notifyItemInserted(chatAdapter.getItemCount() - 1);
                        getViewBinding().recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                    }
                }
            });
        }
    };


    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioRecorder = new AudioRecorder(getBaseActivity(), this::callback);
        audioRecorder.checkRecorderPermission();
        setUpSendAction();

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

    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.VERTICAL, false));
        chatAdapter = new ChatMessageAdapter(getMyContext(), this, getViewBinding().recyclerView, mediaPlayer);
        getViewBinding().recyclerView.setAdapter(chatAdapter);
        chatAdapter.setOnLoadMoreListener(new ChatMessageAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    chatAdapter.addItem(0, null);
                    notifyAdapter();
                    setLoadMore(true);
                    getData();
                }
            }
        });
    }

    private void getData() {
        if (!isLoadMore()) {
            enableLoading = true;
        }
        getDataManager().getMessagesService().getChatMessages(getMyContext(), enableLoading, chat.getId(), isLoadMore ?
                chatAdapter.getItemCount() : 0, new APICallBack<ArrayList<ChatObject>>() {
            @Override
            public void onSuccess(ArrayList<ChatObject> response) {
                checkIsLoadMoreAndRefreshing(true);
                chatAdapter.addItems(response);
                getViewBinding().recyclerView.scrollToPosition(response.size() - 1);
                notifyAdapter();
                canLoadMore = true;
            }

            @Override
            public void onError(String error, int errorCode) {
                if (isLoadMore) {
                    canLoadMore = false;
                }
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

    public void onSendClick() {

    }

    public void onProfileClicked(){
        if(chat!=null){
            Bundle data = new Bundle();
            data.putInt(AppConstants.BundleData.USER_ID,chat.getUser().getId());
            Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                    .navigate(R.id.userProfileFragment,data);
        }
    }

    public void onProductClicked(){
        if(chat!=null){
            Bundle data = new Bundle();
            data.putInt(AppConstants.BundleData.PRODUCT_ID,chat.getPost().getId());
            Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                    .navigate(R.id.productDetailsFragment,data);
        }
    }

    @Override
    protected void setUp() {
        initiateSocket();

        if (getNavigator().getChatId() == -1) {
            chat = getNavigator().getChat();
            joinRoom(chat.getId());
            init();
        } else {
            getChatById(getNavigator().getChatId());
        }
        getViewBinding().toolbar.toolbar.setNavigationOnClickListener(v -> {
            getBaseActivity().onFragmentDetached("TAG");
            popUp();
        });
    }

    @Override
    public void callback(String recordPath) {
        if (recordPath != null) {
            showChat(inSideMessageId, "voice", recordPath);
            sendImageMessage(recordPath, inSideMessageId);
            inSideMessageId = inSideMessageId - 1;
        } else {
            showSnackBar(getMyContext().getResources().getString(R.string.warning),
                    getMyContext().getResources().getString(R.string.the_voice_message_too_short),
                    getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
        }
    }

    private void setUpSendAction() {
        getViewBinding().btnSend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getViewBinding().edMessage.getText().toString().length() == 0) {
                    audioRecorder.recordAction(event,getViewBinding().btnSend);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showChat(inSideMessageId, "text", getViewBinding().edMessage.getText().toString());
                        sendTxtMessage(getViewBinding().edMessage.getText().toString(), inSideMessageId);
                        inSideMessageId = inSideMessageId - 1;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getViewBinding().edMessage.setText("");
                            }
                        }, 100);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(ChatObject chatObject, int position, boolean isResend) {
        if (isResend) {
            chatAdapter.getItem(position).setSent(true);
            chatAdapter.notifyItemChanged(position);
            sendTxtMessage(chatObject.getMessage(), chatObject.getId());
        } else {
            if (chatObject.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)) {
                AudioPlayerDialog audioPlayerDialog = new AudioPlayerDialog(getMyContext(), chatObject);
                audioPlayerDialog.show();
            } else if (chatObject.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)) {
                acceptOffer(chatObject.getOffer_id(), position);
            }
        }

    }

    private void getChatById(int chatId) {
        getDataManager().getMessagesService().getChatById(getMyContext(), true, chatId, new APICallBack<Chat>() {
            @Override
            public void onSuccess(Chat response) {
                chat = response;
                getViewBinding().setData(chat);
                getNavigator().setUpToolbar(chat);
                joinRoom(chat.getId());
                init();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();

                            }
                        });
                popUp();
            }
        });
    }

    private void showChat(int position, String type, String message) {
        ChatObject chatObject = new ChatObject(position, type, message, "now",
                new Sender((int) User.getInstance().getUserID(),
                        User.getInstance().getAvatar(),
                        User.getInstance().getName()));
        if (type.equals("voice")) {
            chatObject.setShowProgress(true);
        }
        chatAdapter.addItem(chatObject);
        chatAdapter.notifyItemInserted(chatAdapter.getItemCount() - 1);
        getViewBinding().recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    public void sendTxtMessage(String message, int inSideMessageId) {
        getDataManager().getMessagesService().sendTextMessage(getMyContext(), false,
                chat.getId(), message, "text", new APICallBack<ChatObject>() {
                    @Override
                    public void onSuccess(ChatObject response) {
                        onMessageSent(response, inSideMessageId);
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        onErrorSend("text", inSideMessageId);
                    }
                });
    }

    public void sendImageMessage(String image, int inSideMessageId) {
        getDataManager().getMessagesService().sendImageMessage(getMyContext(), false,
                chat.getId(), image, "voice", new APICallBack<ChatObject>() {
                    @Override
                    public void onSuccess(ChatObject response) {
                        onMessageSent(response, inSideMessageId);
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        onErrorSend("voice", inSideMessageId);
                    }
                });

    }

    public void acceptOffer(int messageId, int position) {
        getDataManager().getMessagesService().acceptOffer(getMyContext(), true,
                messageId, new APICallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        chatAdapter.getItem(position).getOffer().setStatus(AppConstants.OFFER_STATUS.APPROVED);
                        chatAdapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        showSnackBar(getMyContext().getResources().getString(R.string.error),
                                error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                                    @Override
                                    public void onActionClick(Snackbar snackbar) {
                                        snackbar.dismiss();
                                    }
                                });
                    }
                });
    }

    public void onMessageSent(ChatObject chatObject, int inSideMessageId) {
        if (chatObject.getMessage_type().equals("text")) {
            updateMessageId(inSideMessageId, chatObject);
        } else {
            updateMessageIdAndImage(inSideMessageId, chatObject);
        }
//        notifyAdapter();
    }

    public void onErrorSend(String messageType, int inSideMessageId) {
        showResendInMessage(inSideMessageId);
        if (messageType.equals("voice")) {
            updateImageProgress(inSideMessageId, false);
        }
//        notifyAdapter();
    }

    public void showResendInMessage(int inSideMessageId) {
        for (int i = 0; i < chatAdapter.getItemCount(); i++) {
            if (chatAdapter.getItem(i).getId() == inSideMessageId) {
                chatAdapter.getItem(i).setSent(false);
                chatAdapter.notifyItemChanged(i);
            }
        }
    }

    public void updateMessageId(int inSideMessageId, ChatObject chatObject) {
        for (int i = 0; i < chatAdapter.getItemCount(); i++) {
            if (chatAdapter.getItem(i).getId() == inSideMessageId) {
                chatAdapter.getItem(i).setId(chatObject.getId());
                chatAdapter.getItem(i).setDate(chatObject.getDate());
                chatAdapter.notifyItemChanged(i);
            }
        }
//        messages.get(position).setId(messageId);
    }

    public void updateMessageIdAndImage(int inSideMessageId,
                                        ChatObject chatObject) {
        for (int i = 0; i < chatAdapter.getItemCount(); i++) {
            if (chatAdapter.getItem(i).getId() == inSideMessageId) {
                updateImageProgress(inSideMessageId, false);
                chatAdapter.getItem(i).setId(chatObject.getId());
                chatAdapter.getItem(i).setMessage(chatObject.getMessage());
                chatAdapter.getItem(i).setDate(chatObject.getDate());
                chatAdapter.notifyItemChanged(i);
            }
        }
    }

    protected void initiateSocket() {

        try {
            IO.Options opts = new IO.Options();
            //opts.forceNew = true;
            opts.reconnection = true;

            mSocket = IO.socket(ApiConstants.SOCKET_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
        if (mSocket != null && mSocket.connected() && chat != null) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("chat_id", chat.getId());
                mSocket.emit("leave_room", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //mSocket.disconnect();
        }
    }

    protected void joinRoom(int chatId) {
//        if (mSocket != null &&
////                !mSocket.connected()) {
////            mSocket.connect();
////            return;
////        }

        if (mSocket.connected()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("chat_id", chatId);
                mSocket.emit("join_room", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    private void checkIsLoadMoreAndRefreshing(boolean isSuccess) {
        if (isLoadMore()) {
            finishLoadMore();
        } else {
            enableLoading = false;
        }
    }

    public void finishLoadMore() {
        chatAdapter.remove(0);
        chatAdapter.notifyItemRemoved(-1);
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

    private Emitter.Listener onConnect = args ->
            getBaseActivity().runOnUiThread(() -> {
//                joinRoom();
            });
    private Emitter.Listener onDisconnect = args ->
            getBaseActivity().runOnUiThread(() -> {
            });
    private Emitter.Listener onConnectError = args ->
            getBaseActivity().runOnUiThread(() -> {
            });

    public void updateImageProgress(int inSideMessageId, boolean showProgress) {
        for (int i = 0; i < chatAdapter.getItemCount(); i++) {
            if (chatAdapter.getItem(i).getId() == inSideMessageId) {
                chatAdapter.getItem(i).setShowProgress(showProgress);
                chatAdapter.notifyItemChanged(i);
            }
        }
//        messages.get(position).setShowProgress(showProgress);
    }

}
