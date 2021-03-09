package _02_controller.backStage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import _01_model._07_billboard.Billboard;
import _01_model._07_billboard.BillboardService;

@Controller
@RequestMapping(value = "/manager")
public class B03_announce {

	@Autowired
	private HttpServletRequest request;

	private BillboardService biService;

	@Autowired
	public B03_announce(BillboardService biService) {
		this.biService = biService;
	}

	// 轉移至新增公告頁面
	@RequestMapping(value = "/InsertAnnouncement", method = RequestMethod.GET)
	public String InsertAnnouncement() {
		return "Backstage/02Announce/InsertAnnouncement";
	}

	// 新增公告
	@RequestMapping(value = "/announcementInsert", method = RequestMethod.POST)
	public String announcementInsert(@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			@RequestParam(name = "titleImage") MultipartFile[] titleImage,
			@RequestParam(name = "contentImage1") MultipartFile[] contentImage1,
			@RequestParam(name = "contentImage2") MultipartFile[] contentImage2,
			@RequestParam(name = "status") String status) throws IllegalStateException, IOException, ParseException {
		HttpSession session = request.getSession();
		String mes = (String) session.getAttribute("message");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date d = new Date();
		String p1 = "noPic.png";
		String p2 = "noPic.png";
		String p3 = "noPic.png";
		Billboard b = B03_announce.setbean(title, content, titleImage, contentImage1, contentImage2, status, p1, p2,
				p3);
		b.setTime(d);
		biService.add(b);

		String date = sdf.format(d);
		session.setAttribute("message", date + "─────公告\"" + title + "\"新增成功<br>" + mes);
		return "redirect: " + request.getContextPath() + "/manager/HomePage";
	}

	// 轉移至更新公告頁面 (取值)
	@RequestMapping(value = "/announcementList/{key}", method = RequestMethod.GET)
	public String AnnouncementSelectOne2(@PathVariable String key) {
//			HttpSession session = request.getSession();
//			int announcement_id = (int) session.getAttribute("announcement_id");
		int id = Integer.parseInt(key);

		Billboard bi = biService.selectOne(id);
		SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dd = bi.getTime();

		String time = ff.format(dd);

		Map<String, String> bill_select = new HashMap<String, String>();
		bill_select.put("announcement_id", key);
		bill_select.put("announcement_title", bi.getTitle());
		bill_select.put("announcement_picture1", bi.getPicture1());
		bill_select.put("announcement_content", bi.getContent());
		bill_select.put("announcement_picture2", bi.getPicture2());
		bill_select.put("announcement_picture3", bi.getPicture3());
		if (bi.getStatus().equals("off")) {
			bill_select.put("announcement_status", "checked");
		}
		bill_select.put("announcement_time", time);

		System.out.println("bill_select:" + bill_select);
		request.setAttribute("MAP_Announcement_PreUpdate", bill_select);

		return "Backstage/02Announce/UpdateAnnouncement";
	}

	// 公告更新
	@RequestMapping(value = "/announcementUpdate", method = RequestMethod.POST)
	public String announcementUpdate(@RequestParam(name = "announcement_id") int announcement_id,
			@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,
			@RequestParam(name = "titleImage") MultipartFile[] titleImage,
			@RequestParam(name = "contentImage1") MultipartFile[] contentImage1,
			@RequestParam(name = "contentImage2") MultipartFile[] contentImage2,
			@RequestParam(name = "status") String status) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String mes = (String) session.getAttribute("message");
//		int billboard_id = (int) session.getAttribute("billboard_id");
		Billboard bbb = biService.selectOne(announcement_id);
		String p1 = bbb.getPicture1();
		String p2 = bbb.getPicture2();
		String p3 = bbb.getPicture3();
		Billboard b = B03_announce.setbean(title, content, titleImage, contentImage1, contentImage2, status, p1, p2,
				p3);
		b.setBillboard_id(announcement_id);

		biService.update(b);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date = sdf.format(new Date());

		session.setAttribute("message", date + "─────公告\"" + title + "\"更新成功<br>" + mes);
		return "redirect: " + request.getContextPath() + "/manager/HomePage";
	}

	// 刪除公告 //有動過
	@RequestMapping(value = "/announcementDelete/{key}", method = RequestMethod.GET)
	public String announcementDelete(@PathVariable String key) {
		HttpSession session = request.getSession();
		String mes = (String) session.getAttribute("message"); // new
		int id = Integer.parseInt(key);
		String title = biService.selectOne(id).getTitle(); // new
		biService.delete(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm"); // new
		String date = sdf.format(new Date()); // new
		session.setAttribute("message", date + "─────公告\"" + title + "\"刪除成功<br>" + mes); // new
		return "redirect: " + request.getContextPath() + "/manager/HomePage"; // new
//		request.setAttribute("announcement_delete_success", "公告刪除成功"); //old
//		return "Backstage/02Announce/AnnouncementList"; //old
	}

	// 後台顯示所有公告 //有動過
	@RequestMapping(value = "/show_Announcement", method = RequestMethod.GET)
	public String show_Announcement() {
		HttpSession session = request.getSession();
		ArrayList<Billboard> bill = biService.selectAll();
		JSONArray jsonArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		for (int y = 0; y < bill.size(); y++) {
			Billboard bbb = bill.get(y);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("announcement_id", bbb.getBillboard_id());
			jsonObject.put("announcement_title", bbb.getTitle());
			jsonObject.put("announcement_picture1", bbb.getPicture1());
			jsonObject.put("announcement_content", bbb.getContent());
			Date dd = bbb.getTime();
			String time = sdf.format(dd);
			jsonObject.put("announcement_time", time);
			String status = "上架中";
			if (bbb.getStatus().equals("on")) {
				jsonObject.put("announcement_status", status);
			} else {
				status = "下架中";
				jsonObject.put("announcement_status", status);
			}
			jsonArray.put(jsonObject);
		}
		session.setAttribute("JSON_Announcement_ListAll", jsonArray.toString());
		return "Backstage/02Announce/AnnouncementList";
	}

	// 前台顯示上架公告 //有動過
	@RequestMapping(value = "/announcement_SelectStatus", method = RequestMethod.GET)
	public String AnnouncementSelectStatus() {
//		HttpSession session = request.getSession();
		ArrayList<Billboard> bill = biService.selectAll();
		JSONArray jsonArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		for (int y = 0; y < bill.size(); y++) {
			Billboard bbb = bill.get(y);
			String st = bbb.getStatus();
			if (st.equals("on")) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("announcement_id", bbb.getBillboard_id());
				jsonObject.put("announcement_title", bbb.getTitle());
				jsonObject.put("announcement_picture1", bbb.getPicture1());
				jsonObject.put("announcement_content", bbb.getContent());
				Date dd = bbb.getTime();
				String time = sdf.format(dd);
				jsonObject.put("announcement_time", time);
				jsonArray.put(jsonObject);
			}
		}
		request.setAttribute("JSON_Announcement_SelectStatus", jsonArray.toString());
		return "Backstage/02Announce/AnnouncementSelectStatus";
	}

	// 顯示單一公告
	@RequestMapping(value = "/announcementSelectOne/{key}", method = RequestMethod.GET)
	public String AnnouncementSelectOne(@PathVariable String key) {
//		HttpSession session = request.getSession();
//		int announcement_id = (int) session.getAttribute("announcement_id");

		int id = Integer.parseInt(key);
		Billboard bi = biService.selectOne(id);
		SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dd = bi.getTime();

		String time = ff.format(dd);
		Map<String, String> bill_select = new HashMap<String, String>();
		bill_select.put("announcement_title", bi.getTitle());
		bill_select.put("announcement_picture1", bi.getPicture1());
		bill_select.put("announcement_content", bi.getContent());
		bill_select.put("announcement_picture2", bi.getPicture2());
		bill_select.put("announcement_picture3", bi.getPicture3());
		String status = "上架中";
		if (bi.getStatus().equals("on")) {
			bill_select.put("announcement_status", status);
		} else {
			status = "下架中";
			bill_select.put("announcement_status", status);
		}
		bill_select.put("announcement_time", time);

		System.out.println("bill_select:" + bill_select);
		request.setAttribute("MAP_Announcement_SelectOne", bill_select);

		return "Backstage/02Announce/AnnouncementSelectOne";
	}

	// 新增/修改公告Bean
	private static Billboard setbean(String title, String content, MultipartFile[] titleImage,
			MultipartFile[] contentImage1, MultipartFile[] contentImage2, String status, String p1, String p2,
			String p3) {
		Billboard bb = new Billboard();
		bb.setTitle(title);
		bb.setContent(content);

		bb.setStatus(status);
		SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dd = new Date();

		String time = ff.format(dd);

		for (MultipartFile multipartfile : titleImage) {
			String abc = "a";
			B03_announce.fileUpload(multipartfile, time, abc, bb, p1);
		}
		for (MultipartFile multipartfile : contentImage1) {
			String abc = "b";
			B03_announce.fileUpload(multipartfile, time, abc, bb, p2);
		}
		for (MultipartFile multipartfile : contentImage2) {
			String abc = "c";
			B03_announce.fileUpload(multipartfile, time, abc, bb, p3);
		}
		System.out.println("p1:" + p1);
		System.out.println("p2:" + p2);
		System.out.println("p3:" + p3);
		return bb;
	}

	// 上傳多張照片
	private static void fileUpload(MultipartFile multipartfile, String nameId, String abc, Billboard bb, String p) {
		try {
			if (!multipartfile.isEmpty()) {
				String fileName = multipartfile.getOriginalFilename();
				String ext = FilenameUtils.getExtension(fileName);// 取得副檔名
				String name = nameId + "-" + abc;
				String savePath = String.format(
						"D:/DataSource/Presentation/Presentation/WebContent/WEB-INF/resources/images/billboardPics/%s.%s",
						name, ext);
				if (ext.equals("bmp") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")
						|| ext.equals("png")) {
					if (fileName != null && fileName.length() != 0) {
						multipartfile.transferTo(new File(savePath));
						if (abc.equals("a")) {
							bb.setPicture1(name + "." + ext);
						}
						if (abc.equals("b")) {
							bb.setPicture2(name + "." + ext);
						}
						if (abc.equals("c")) {
							bb.setPicture3(name + "." + ext);
						}
					}
				}
			} else {
				if (abc.equals("a")) {
					bb.setPicture1(p);
				}
				if (abc.equals("b")) {
					bb.setPicture2(p);
				}
				if (abc.equals("c")) {
					bb.setPicture3(p);
				}
			}
		} catch (Exception e) {

		}
	}
}