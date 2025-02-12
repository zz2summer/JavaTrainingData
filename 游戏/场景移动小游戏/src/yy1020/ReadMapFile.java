package yy1020;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

/**
 * 读入地图文件
 * 
 * @author yy
 * 
 */
public class ReadMapFile {
	// 定义静态的三个数组，用来保存从地图文件中读取到的三个地图数组
	static int[][] map1;
	static int[][] map2;
	static int[][] map3;

	/**
	 * 读入地图
	 * 
	 * @param path
	 *            地图文件位置
	 */
	static void readfile(String path) {
		if (new File(path).exists()) {
			try {
				// 从path路径下的地图文件中得到文件输入流
				FileInputStream fis = new FileInputStream(path);
				// 将文件输入流包装成基本数据输入流
				DataInputStream dis = new DataInputStream(fis);
				// 按保存时候的顺序依次读出地图文件中的三个地图数组
				int i = dis.readInt();
				int j = dis.readInt();
				map1 = new int[i][j];
				map2 = new int[i][j];
				map3 = new int[i][j];
				for (int ii = 0; ii < i; ii++) {
					for (int jj = 0; jj < j; jj++) {
						map1[ii][jj] = dis.readInt();
						map2[ii][jj] = dis.readInt();
						map3[ii][jj] = dis.readInt();
					}
				}
				dis.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RandomMapInit();
		}
	}

	public static void RandomMapInit() {
		int[] ms = new int[] { 0, 1, 2, 3, 100, 101, 102, 103, 150 };
		int i = new Random().nextInt(500) + 1;
		int j = new Random().nextInt(500) + 1;
		map1 = new int[i][j];
		map2 = new int[i][j];
		map3 = new int[i][j];
		for (int ii = 0; ii < i; ii++) {
			for (int jj = 0; jj < j; jj++) {
				map1[ii][jj] = ms[new Random().nextInt(ms.length)];
				map2[ii][jj] = ms[new Random().nextInt(ms.length)];
				map3[ii][jj] = ms[new Random().nextInt(ms.length)];
			}
		}
	}
}
