package _02_controller.member.supply;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@RequestMapping(value = "/shop")
public class S05_setting {
	@Autowired
	private HttpServletRequest request;

	private SupplyService suservice;

	@Autowired
	private S05_setting(SupplyService suservice) {
		this.suservice = suservice;
	}

	// 顯示會員資料
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String show_data() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		Supply sup = suservice.selectOne(supply_id);

		Map<String, String> supply_data = new HashMap<String, String>();
		supply_data.put("name", sup.getSupply_name());
		supply_data.put("ph", sup.getSupply_ph());
		supply_data.put("address", sup.getSupply_address());
		supply_data.put("contact", sup.getSupply_contact());
		supply_data.put("contactphnum", sup.getSupply_contactphnum());
		supply_data.put("email", sup.getSupply_email());
		supply_data.put("conumber", sup.getSupply_conumber());

		supply_data.put("bankaccount", sup.getSupply_bankaccount());
		supply_data.put("invoice", sup.getSupply_invoice());

		String vip;
		if (sup.getSupply_vip().equals("member")) {
			vip = "一般會員";
		} else if (sup.getSupply_vip().equals("vip")) {
			vip = "付費會員";
			request.setAttribute("display", "none");
		} else if (sup.getSupply_vip().equals("unverified") || sup.getSupply_vip().equals("banned")) {
			vip = "不可使用";
			request.setAttribute("display", "none");
		} else {
			vip = "待審核...";
			request.setAttribute("display", "none");
		}

		supply_data.put("vip", vip);
		supply_data.put("image", sup.getSupply_image());

		request.setAttribute("supply_data", supply_data);

		request.setAttribute("limit", sup.getLimit());

		return "03ShopPages/01RegPages/ShopRegInfo";
	}

	// 新增或更改會員頭像
	@RequestMapping(value = "/data/picchange", method = RequestMethod.POST)
	private String pic_change(@RequestParam(name = "picFiles") MultipartFile multipartfile)
			throws IllegalStateException, IOException {
		if (multipartfile.isEmpty() != true) {
			HttpSession session = request.getSession();
			int supply_id = (int) session.getAttribute("supply_id");

			String fileName = multipartfile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(fileName);

			String Path = "D:/DataSource/Presentation/Presentation/WebContent/WEB-INF/resources/images/supplyPics/";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String name = "SUPPLY_" + supply_id + "_" + sdf.format(new Date()) + "." + ext;
//				取得副檔名
			String savePath = Path + name;
			String originalPath = Path + suservice.selectOne(supply_id).getSupply_image();
			if (ext.equals("bmp") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")
					|| ext.equals("png")) {
				if (fileName != null && fileName.length() != 0) {
					File file = new File(originalPath);
					if (file.exists()) {
						file.delete();
					}
					
					File newfile = new File(savePath);
					multipartfile.transferTo(newfile);

					Supply sup = new Supply(supply_id);
					sup.setSupply_image(name);
					suservice.update_image(sup);
				}
			}

		}
		return "redirect: " + request.getContextPath() + "/shop/data";
	}

	// 輸入繳費代碼
	@RequestMapping(value = "/data/codeinsert", method = RequestMethod.POST)
	private String vip_change(@RequestParam(name = "code") String code) {
		if (code != null && code.length() != 0) {
			HttpSession session = request.getSession();
			int supply_id = (int) session.getAttribute("supply_id");
			Supply s = new Supply(supply_id);

			if (code.equals("vip")) {
				s.setSupply_vip("banned");
				return "redirect: " + request.getContextPath() + "/welcome/";
			} else {
				s.setSupply_vip(code);
			}

			suservice.update_vip(s);
		}

		return "redirect: " + request.getContextPath() + "/shop/logout";
	}

	// 顯示修改會員資料頁面
	@RequestMapping(value = "/data/update", method = RequestMethod.POST)
	public String show_data_update() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		Supply sup = suservice.selectOne(supply_id);

		Map<String, String> supply_data = new HashMap<String, String>();
		supply_data.put("name", sup.getSupply_name());
		supply_data.put("ph", sup.getSupply_ph());
		supply_data.put("address", sup.getSupply_address());
		supply_data.put("contact", sup.getSupply_contact());
		supply_data.put("contactphnum", sup.getSupply_contactphnum());
		supply_data.put("email", sup.getSupply_email());
		supply_data.put("conumber", sup.getSupply_conumber());

		supply_data.put("bankaccount", sup.getSupply_bankaccount());
		supply_data.put("invoice", sup.getSupply_invoice());

		String vip;
		if (sup.getSupply_vip().equals("member")) {
			vip = "一般會員";
		} else if (sup.getSupply_vip().equals("vip")) {
			vip = "付費會員";
		} else {
			vip = "不可使用";
		}
		supply_data.put("vip", vip);
		supply_data.put("image", sup.getSupply_image());
		request.setAttribute("supply_data", supply_data);

		request.setAttribute("limit", sup.getLimit());

		return "03ShopPages/01RegPages/ShopRegInfoCheck";
	}

	// 更改會員資料
	@RequestMapping(value = "/data/update_fail", method = RequestMethod.POST)
	public String data_update(@RequestParam(name = "name") String name, @RequestParam(name = "ph") String ph,
			@RequestParam(name = "address") String address, @RequestParam(name = "contact") String contact,
			@RequestParam(name = "contactphnum") String contactphnum, @RequestParam(name = "email") String email,
			@RequestParam(name = "conumber") String conumber, @RequestParam(name = "bankaccount") String bankaccount,
			@RequestParam(name = "invoice") String invoice, @RequestParam(name = "limit") String limit) {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		boolean error = false;
		try {
			int num = Integer.valueOf(limit);
			if (num < 0) {
				request.setAttribute("ChangeLimit", "請輸入0以上之整數");
				error = true;
			}
		} catch (NumberFormatException e) {
			request.setAttribute("ChangeLimit", "請輸入0以上之整數");
			error = true;
		}

		if (conumber.length() != 8 || !conumber.matches("[\\d]+")) {
			request.setAttribute("ChangeMsg", "統一編號格式錯誤");
			error = true;
		}

		if (ph.length() != 10 || !ph.matches("[\\d]+") || contactphnum.length() != 10
				|| !contactphnum.matches("[\\d]+")) {
			request.setAttribute("ChangeMsg", "電話格式錯誤");
			error = true;
		}

		if (name.length() == 0 || ph.length() == 0 || address.length() == 0 || contact.length() == 0
				|| contactphnum.length() == 0 || email.length() == 0 || conumber.length() == 0
				|| invoice.length() == 0) {
			request.setAttribute("ChangeMsg", "基本資料不可留白");
			error = true;
		}

		if (error) {
			Map<String, String> supply_data = new HashMap<String, String>();
			supply_data.put("name", name);
			supply_data.put("ph", ph);
			supply_data.put("address", address);
			supply_data.put("contact", contact);
			supply_data.put("contactphnum", contactphnum);
			supply_data.put("email", email);
			supply_data.put("conumber", conumber);
			supply_data.put("invoice", invoice);

			supply_data.put("bankaccount", bankaccount);
			supply_data.put("vip", suservice.selectOne(supply_id).getSupply_vip());
			supply_data.put("image", suservice.selectOne(supply_id).getSupply_image());
			request.setAttribute("limit", limit);
			request.setAttribute("supply_data", supply_data);
			return "03ShopPages/01RegPages/ShopRegInfoCheck";
		}

		Supply sup = new Supply(supply_id, name, ph, address, contact, contactphnum, email, conumber, bankaccount,
				invoice, Integer.valueOf(limit));
		suservice.update_data(sup);

		return "redirect: " + request.getContextPath() + "/shop/data";
	}

	// 隱私權設定(更改密碼頁面) POST
	@RequestMapping(value = "/data/account/update", method = RequestMethod.GET)
	public String show_account_update() {
		return "03ShopPages/01RegPages/ShopRegPwd";
	}

	// 更改密碼
	@RequestMapping(value = "/data/account/update_fail", method = RequestMethod.POST)
	public String account_update(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "pwd1") String pwd1,
			@RequestParam(value = "pwd2") String pwd2) {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		Supply sup = suservice.selectOne(supply_id);

		// 成功
		if (pwd.equals(sup.getSupply_pwd()) && pwd1.equals(pwd2) && !pwd1.equals(pwd) && !pwd2.equals(pwd)) {
			Supply sup_new = new Supply();
			sup_new.setSupply_id(supply_id);
			sup_new.setSupply_pwd(pwd1);
			suservice.update_pwd(sup_new);

			request.setAttribute("ChangePwdMsg", "密碼更改成功");

			return "redirect: " + request.getContextPath() + "/cstmr/data";
		}

		// 失敗
		request.setAttribute("ChangePwdMsg", "原密碼錯誤，新密碼驗證錯誤，或未更改密碼");

		return "03ShopPages/01RegPages/ShopRegPwd";
	}

}
