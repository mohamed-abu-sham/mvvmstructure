package com.selwantech.raheeb.model;

import android.view.View;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;

import java.io.Serializable;

public class ProductOwner extends BaseObservable implements Serializable {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("email")
	private String email;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("cover_image")
	private String coverImage;

	@SerializedName("login_with")
	private String loginWith;

	@SerializedName("is_valid")
	private boolean isValid;

	@SerializedName("is_blocked")
	private boolean isBlocked;

	@SerializedName("is_follow")
	private boolean isFollow;

	@SerializedName("count_followers")
	private int countFollowers;

	@SerializedName("count_following")
	private int countFollowing;

	@SerializedName("rate")
	private double rate;

	public int getCountFollowers() {
		return countFollowers;
	}

	public void setCountFollowers(int countFollowers) {
		this.countFollowers = countFollowers;
	}

	public boolean isIsFollow() {
		return isFollow;
	}

	public String getFollowText() {
		return !isFollow ? App.getInstance().getApplicationContext().getResources().getString(R.string.follow) :
				App.getInstance().getApplicationContext().getResources().getString(R.string.unfollow);
	}

	public int getFollowColor() {
		return !isFollow ? App.getInstance().getApplicationContext().getResources().getColor(R.color.color_blue) :
				App.getInstance().getApplicationContext().getResources().getColor(R.color.text_gray);
	}
	public int getFollowBackground() {
		return !isFollow ? R.drawable.shape_circuler_blue:
				R.drawable.shape_circuler_gray;
	}

	public int getCountFollowing() {
		return countFollowing;
	}

	public void setCountFollowing(int countFollowing) {
		this.countFollowing = countFollowing;
	}

	public void minusFollower() {
		this.countFollowers = countFollowers - 1;
		notifyChange();
	}

	public void plusFollower() {
		this.countFollowers++;
		notifyChange();
	}

	public String getLoginWith() {
		return loginWith;
	}

	public String getAvatar() {
		return avatar;
	}

	public boolean isIsBlocked() {
		return isBlocked;
	}

	public double getRate() {
		return rate;
	}

	public String getRateString() {
		return String.valueOf(rate);
    }

	public boolean isIsValid() {
		return isValid;
	}

	public int getValidTrusted() {
		return isValid ? View.VISIBLE : View.GONE;
	}

	public int getValidTwitter() {
		return !loginWith.equals("raheeb") ? View.VISIBLE : View.GONE;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getValidPhone() {
		return phoneNumber != null && !phoneNumber.isEmpty() ? View.VISIBLE : View.GONE;
	}

	public int getId() {
		return id;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public String getEmail() {
		return email;
	}

	public int getValidEmail() {
		return email != null && !email.isEmpty() ? View.VISIBLE : View.GONE;
	}

	public void setFollow(boolean follow) {
		isFollow = follow;
		notifyChange();
    }
}