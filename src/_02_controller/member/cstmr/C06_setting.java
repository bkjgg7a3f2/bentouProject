package _02_controller.member.cstmr;

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

import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;

@Controller
@RequestMapping(value = "/cstmr")
public class C06_setting {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;

	@Autowired
	private C06_setting(CustomerService cservice) {
		this.cservice = cservice;
	}

	// 顯示會員資料
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	private String show_data() {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		Customer cus = cservice.selectOne(cstmr_id);

		Map<String, String> cstmr_data = new HashMap<String, String>();
		cstmr_data.put("name", cus.getCstmr_name());
		cstmr_data.put("ph", cus.getCstmr_ph());
		cstmr_data.put("address", cus.getCstmr_address());
		cstmr_data.put("contact", cus.getCstmr_contact());
		cstmr_data.put("contactphnum", cus.getCstmr_contactphnum());
		cstmr_data.put("email", cus.getCstmr_email());
		cstmr_data.put("conumber", cus.getCstmr_conumber());

		cstmr_data.put("bankaccount", cus.getCstmr_bankaccount());
		cstmr_data.put("invoice", cus.getCstmr_invoice());

		String vip;
		if (cus.getCstmr_vip().equals("member")) {
			vip = "一般會員";
		} else if (cus.getCstmr_vip().equals("vip")) {
			vip = "付費會員";
			request.setAttribute("display", "none");
		} else if (cus.getCstmr_vip().equals("unverified") || cus.getCstmr_vip().equals("banned")) {
			vip = "不可使用";
			request.setAttribute("display", "none");
		} else {
			vip = "待審核...";
			request.setAttribute("display", "none");
		}

		cstmr_data.put("vip", vip);
		cstmr_data.put("image", cus.getCstmr_image());

		request.setAttribute("cstmr_data", cstmr_data);

		return "02CustomerPages/01RegPages/CustRegInfo";
	}

	// 新增或更改會員頭像
	@RequestMapping(value = "/data/picchange", method = RequestMethod.POST)
	private String pic_change(@RequestParam(name = "picFiles") MultipartFile multipartfile)
			throws IllegalStateException, IOException {
		if (multipartfile.isEmpty() != true) {
			HttpSession session = request.getSession();
			int cstmr_id = (int) session.getAttribute("cstmr_id");

			String fileName = multipartfile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(fileName);

			String Path = "D:/DataSource/Presentation/Presentation/WebContent/WEB-INF/resources/images/cstmrPics/";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String name = "CSTMR_" + cstmr_id + "_" + sdf.format(new Date()) + "." + ext;

//			取得副檔名
			String savePath = String.format(Path + name);
			String originalPath = String.format(Path + cservice.selectOne(cstmr_id).getCstmr_image());
			if (ext.equals("bmp") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")
					|| ext.equals("png")) {
				if (fileName != null && fileName.length() != 0) {
					File file = new File(originalPath);
					if (file.exists()) {
						file.delete();
					}
					
					File newfile = new File(savePath);
					multipartfile.transferTo(newfile);
					
					Customer cus = new Customer(cstmr_id);
					cus.setCstmr_image(name);
					cservice.update_image(cus);
				}
			}

		}
		return "redirect: " + request.getContextPath() + "/cstmr/data";
	}

	// 輸入繳費代碼
	@RequestMapping(value = "/data/codeinsert", method = RequestMethod.POST)
	private String vip_change(@RequestParam(name = "code") String code) {
		if (code != null && code.length() != 0) {
			HttpSession session = request.getSession();
			int cstmr_id = (int) session.getAttribute("cstmr_id");
			Customer c = new Customer(cstmr_id);

			if (code.equals("vip")) {
				c.setCstmr_vip("banned");
				cservice.update_vip(c);
				return "redirect: " + request.getContextPath() + "/cstmr/logout";
			} else {
				c.setCstmr_vip(code);
			}

			cservice.update_vip(c);
		}

		return "redirect: " + request.getContextPath() + "/cstmr/data";
	}

	// 顯示修改會員資料頁面
	@RequestMapping(value = "/data/update", method = RequestMethod.POST)
	private String show_data_update() {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		Customer cus = cservice.selectOne(cstmr_id);

		Map<String, String> cstmr_data = new HashMap<String, String>();
		cstmr_data.put("name", cus.getCstmr_name());
		cstmr_data.put("ph", cus.getCstmr_ph());
		cstmr_data.put("address", cus.getCstmr_address());
		cstmr_data.put("contact", cus.getCstmr_contact());
		cstmr_data.put("contactphnum", cus.getCstmr_contactphnum());
		cstmr_data.put("email", cus.getCstmr_email());
		cstmr_data.put("conumber", cus.getCstmr_conumber());

		cstmr_data.put("bankaccount", cus.getCstmr_bankaccount());
		cstmr_data.put("invoice", cus.getCstmr_invoice());

		String vip;
		if (cus.getCstmr_vip().equals("member")) {
			vip = "一般會員";
		} else if (cus.getCstmr_vip().equals("vip")) {
			vip = "付費會員";
		} else if (cus.getCstmr_vip().equals("unverified") || cus.getCstmr_vip().equals("banned")) {
			vip = "不可使用";
		} else {
			vip = "待審核...";
		}
		cstmr_data.put("vip", vip);
		cstmr_data.put("image", cus.getCstmr_image());
		request.setAttribute("cstmr_data", cstmr_data);

		return "02CustomerPages/01RegPages/CustRegInfoCheck";
	}

	// 更改會員資料
	@RequestMapping(value = "/data/update_fail", method = RequestMethod.POST)
	private String data_update(@RequestParam(name = "name") String name, @RequestParam(name = "ph") String ph,
			@RequestParam(name = "address") String address, @RequestParam(name = "contact") String contact,
			@RequestParam(name = "contactphnum") String contactphnum, @RequestParam(name = "email") String email,
			@RequestParam(name = "conumber") String conumber, @RequestParam(name = "bankaccount") String bankaccount,
			@RequestParam(name = "invoice") String invoice) {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		boolean error = false;

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
			Map<String, String> cstmr_data = new HashMap<String, String>();
			cstmr_data.put("name", name);
			cstmr_data.put("ph", ph);
			cstmr_data.put("address", address);
			cstmr_data.put("contact", contact);
			cstmr_data.put("contactphnum", contactphnum);
			cstmr_data.put("email", email);
			cstmr_data.put("conumber", conumber);
			cstmr_data.put("invoice", invoice);

			cstmr_data.put("bankaccount", bankaccount);

			String vip;
			Customer cus = cservice.selectOne(cstmr_id);
			if (cus.getCstmr_vip().equals("member")) {
				vip = "一般會員";
			} else if (cus.getCstmr_vip().equals("vip")) {
				vip = "付費會員";
			} else if (cus.getCstmr_vip().equals("unverified") || cus.getCstmr_vip().equals("banned")) {
				vip = "不可使用";
			} else {
				vip = "待審核...";
			}

			cstmr_data.put("vip", vip);
			cstmr_data.put("image", cus.getCstmr_image());
			request.setAttribute("cstmr_data", cstmr_data);
			return "02CustomerPages/01RegPages/CustRegInfoCheck";
		}

		Customer cus = new Customer(cstmr_id, name, ph, address, contact, contactphnum, email, conumber, bankaccount,
				invoice);

		cservice.update_data(cus);

		return "redirect: " + request.getContextPath() + "/cstmr/data";
	}

	// 隱私權設定(更改密碼頁面) POST
	@RequestMapping(value = "/data/account/update", method = RequestMethod.GET)
	public String show_account_update() {
		return "02CustomerPages/01RegPages/CustRegPwd";
	}

	// 更改密碼
	@RequestMapping(value = "/data/account/update_fail", method = RequestMethod.POST)
	public String account_update(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "pwd1") String pwd1,
			@RequestParam(value = "pwd2") String pwd2) {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		Customer cus = cservice.selectOne(cstmr_id);

		// 成功
		if (pwd.equals(cus.getCstmr_pwd()) && pwd1.equals(pwd2) && !pwd1.equals(pwd) && !pwd2.equals(pwd)) {
			Customer cus_new = new Customer();
			cus_new.setCstmr_id(cstmr_id);
			cus_new.setCstmr_pwd(pwd1);
			cservice.update_pwd(cus_new);

			request.setAttribute("ChangePwdMsg", "密碼更改成功");

			return "redirect: " + request.getContextPath() + "/cstmr/data";
		}

		// 失敗
		request.setAttribute("ChangePwdMsg", "原密碼錯誤，新密碼驗證錯誤，或未更改密碼");

		return "02CustomerPages/01RegPages/CustRegPwd";
	}

}
