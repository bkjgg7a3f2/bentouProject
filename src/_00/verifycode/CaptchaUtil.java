package _00.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtil {
	private BufferedImage image;// 影象
	private String str;// 驗證碼
	private static char code[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();
	public static final String SESSION_CODE_NAME = "code";

	public CaptchaUtil() {
		init();// 初始化屬性
	}

	// 取得RandomNumUtil例項
	public static CaptchaUtil Instance() {
		return new CaptchaUtil();
	}

	// 取得驗證碼圖片
	public BufferedImage getImage() {
		return this.image;
	}

	// 取得圖片的驗證碼
	public String getString() {
		return this.str;
	}

	private void init() {
		// 在記憶體中建立圖象
		int width = 85, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 獲取圖形上下文
		Graphics g = image.getGraphics();
		// 生成隨機類
		Random random = new Random();
		// 設定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 設定字型
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 隨機產生155條幹擾線，使圖象中的認證碼不易被其它程式探測到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);//0~84隨機取一個
			int y = random.nextInt(height);//0~19隨機取一個
			int x1 = random.nextInt(12);//0~11隨機取一個
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x + x1, y + y1);
		}
		// 取隨機產生的認證碼(4位數字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(code[random.nextInt(code.length)]);
			sRand += rand;
			// 將認證碼顯示到圖象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 呼叫函數出來的顏色相同，可能是因為種子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}
		this.str = sRand;
		// 圖象生效
		g.dispose();
		this.image = image;/* 賦值影象 */
	}

	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
