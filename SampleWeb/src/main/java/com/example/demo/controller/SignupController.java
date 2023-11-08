package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.SignupMessage;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録画面 Controller
 * 
 * @author yuuuu
 * 
 */
@Controller
@RequiredArgsConstructor

public class SignupController {
	
	/** ユーザー登録画面service */
	private final SignupService service;

	/** メッセージソース */
	private final MessageSource messageSouece;

	/**
	 * 初期表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面(ログイン)
	 */
	@GetMapping("/signup")
	public String view(Model model, SignupForm form) {
		return "signup";
	}
	
	/**
	 *ユーザー登録 
	 *
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面(メインメニュー or ログイン)
	 */
	@PostMapping("/signup")
	public void signup(Model model,SignupForm form) {
		var userInfoOpt = service.resistUserInfo(form);
		var signupMessage = judgeMessageKey(userInfoOpt);
		var messageId = AppUtil.getMessage(messageSouece,signupMessage.getMessageId());
		model.addAttribute("message",messageId);
		model.addAttribute("isError",signupMessage.isError());
	}
	
	/**
	 * ユーザ情報登録の結果メッセージキーを判断する。
	 * 
	 * @param userInfoOpt ユーザ登録結果(登録済みだった場合はEmpty)
	 * @return メッセージキー
	 */
	private SignupMessage judgeMessageKey(Optional<UserInfo> userInfoOpt) {
		if (userInfoOpt.isEmpty()) {
			return SignupMessage.EXISTED_LOGIN_ID;
		}else {
			return SignupMessage.SUCCEED;
			
		}
	}
}





