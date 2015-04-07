/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package branch;

/**
 *
 * @author adek
 */
public class Simpul {
    public int[][] M;
	public int dimensi;//dimensi matriks M
	public int no;
	public int cost;
	public int r;//untuk  total nilai pengurang reduksi
	public Simpul(int dimensi,int no) {
		this.dimensi = dimensi;
		M = new int[dimensi][dimensi];
		this.no = no;
	}
	public void CetakM() {
    for(int i=1;i<dimensi;i++) {
      for(int j=1;j<dimensi;j++) 
        System.out.print(M[i][j] + " ");
      System.out.println();
    }
  }
	public void CopyM(int[][] M) {
	//mencopy M ke this.M
		for(int i=1;i<dimensi;i++) {
			for(int j=1;j<dimensi;j++)
				this.M[i][j] = M[i][j];
		}
	}
	public void ReduksiM()  {
    //I.S : r dinisialisasi dengan 0
    //F.S : m[n][n] tereduksi, dan r = total(min) 
    //Proses : menghitung r dan mereduksi M[][]
    //asumsi : 0 < elemen matriks < 1000
    //seleksi baris
    int min; r=0;
    for(int i=1;i<dimensi;i++) {
      min = 1000;  int j=1;
      //cari nilai minimum per baris
      while (j<dimensi && min>0) {
        if(min>M[i][j]) min = M[i][j];
        j++;
      }
      //kurangi jika min>0
      if(min>0 && min < 1000) {
        for(j=1;j<dimensi;j++) 
          M[i][j]-=min; 
      	r += min;
      }
    }
    //seleksi kolom
    for(int j=1;j<dimensi;j++) {
      min = 1000; int i=1;
      //cari nilai minimum per kolom
      while(i<dimensi && min>0) {
        if(min>M[i][j]) min = M[i][j];
        i++;
      }
      //kurangi jika min>0
      if(min>0 && min < 1000) {
        for(i=1;i<dimensi;i++) 
          M[i][j] -= min;
      	r+= min;
      }
    }
    //refresh
    for(int i=0;i<dimensi;i++) {
    	for(int j=0;j<dimensi;j++)
    		if(M[i][j] > 1000) 
    			M[i][j] = 10000;
    }
  }
  public Simpul CostAnak(int j) {
  	//memberikan keluaran cost anak simpul tsb
  	Simpul son = new Simpul(dimensi,j);
  	son.CopyM(M);//menyalin M ke son.M
  	int costhere = M[no][j]>1000?0:M[no][j];
  	for(int k=1;k<dimensi;k++) {	 
  		son.M[no][k] = 10000;
  		son.M[k][j] = 10000;
  	}
  	son.M[j][1] = 10000;
  	son.ReduksiM();
  	son.cost = costhere + cost + son.r;
  	return son;
  }
    
}
