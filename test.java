Skip to content
Search or jump to…

Pull requests
Issues
Marketplace
Explore
 
@MercuryJ29 
Learn Git and GitHub without any code!
Using the Hello World guide, you’ll start a branch, write comments, and open a pull request.


MercuryJ29
/
Pagerank
1
00
 Code
 Issues 0
 Pull requests 0 Actions
 Projects 0
 Wiki
 Security 0
 Insights
 Settings
Pagerank/implement_usingJava
@MercuryJ29 MercuryJ29 basic implement
dacfe20 on 23 Nov 2019
120 lines (111 sloc)  3.18 KB
  
package basic;

import java.util.Random;
import java.util.Scanner;


/*
 * 实现pagerank算法
 * 采用邻接矩阵（二维数组）实现图的存储结构
 *在之前的realize_pagerank的所有出链为随机比率的基础上改成为0/1概率，即将系数矩阵转换为一个0/1的稀疏矩阵
 *
 * @author:Jiang
 * */
public class realize_pagerank_sparse {
	public static double alpha = 0.85; // 设置比例系数为0.85
	public static double threshold = 0.000000000001; // 设置阈值为0.000000000001
	public static Scanner s = new Scanner(System.in);
	public static int N; // 网页个数
	public static double[][] G; // 邻接矩阵
	public static double[] V; // 向量
	public static int fre = 0; // 记录迭代的次数
	public static Random r=new Random();//生成随机数

	public static void main(String[] args) {
		System.out.println("PageRank算法公式比例系数大小为：" + alpha + "\n阈值大小为：" + threshold + "\n请输入网页个数：");
		N = s.nextInt();
		InitializeGraph();// 矩阵初始化
		InitializeVector();// 向量初始化
		//开始计时
		long startTime=System.nanoTime();
		calculatePagerank();
		//结束计时
		long endTime=System.nanoTime();
		//该算法用时，以纳秒为单位
		long totalTime=endTime-startTime;
		/*
		System.out.println("迭代结束之后的网页向量：");
		for (int i = 0; i < N; i++)
			System.out.println("网页" + i + ":" + V[i]);
			*/
		System.out.print("迭代次数："+fre+"\n用时："+totalTime);
		
	}

	/*
	 * 初始化矩阵 i行j列为1表示网页j有一个指向i的链接 最后更新为每个网页指向对应的网页的比例
	 * 
	 * @param N 网页的个数
	 */

	public static void InitializeGraph() {
		G = new double[N][N];
		//System.out.println("开始矩阵的输入：");
		for (int i = 0; i < N; i++) {// 随机生成： 0.80为1   0.20为0
			for (int j = 0; j < N; j++) {
				if (r.nextInt(10) > 2) {
					G[i][j]=1;
				}else {
					G[i][j]=0;
				}
			}
		}
		for (int j = 0; j < N; j++) {// 更新G，完成初始化
			double sum = 0;
			for (int i = 0; i < N; i++) {
				if (G[i][j] == 1)
					sum++;
			}
			for (int i = 0; i < N; i++) {
				if (G[i][j] == 1)
					G[i][j] = 1 / sum;
			}
		}
	}

	/*
	 * 初始化向量
	 * 
	 * @param N 网页的个数
	 */
	public static void InitializeVector() {
		V = new double[N];
		for (int i = 0; i < N; i++) {
			V[i] = 1/(double)N;
		}
	}

	/*
	 * 迭代更新G矩阵，完成PageRank算法
	 * 
	 * @param N 网页的个数
	 * 
	 * @param alpha 比例系数=0.85
	 * 
	 * @param threshold 阈值=0.000000000001
	 */
	public static void calculatePagerank() {
		double[] tempV = new double[N]; // 生成一个临时的网页向量，用来比较迭代是否达到阈值
		double testThreshold = 0; // 本次迭代的差值
		for (int i = 0; i < N; i++) {
			tempV[i] = V[i];
			V[i] = 0.15*V[i];
		}
		/* 开始更新矩阵 */
		fre++;
		//System.out.println("开始第" + fre + "次迭代");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				V[i]+=0.85*G[i][j] * tempV[j];
			}
		}
		for (int i = 0; i < N; i++) {
			testThreshold += Math.abs(V[i] - tempV[i]);
		}
		if (testThreshold <= threshold)
			return;
		else
			calculatePagerank();
	}
}
© 2020 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
Pricing
API
Training
Blog
About
