package branch;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
public class Branch {
    //library graphstrip
    public static void main(String args[]) {
        
        //BACA FILE
        File f;
        Scanner scan = null;
        try {
          f = new File("input4.txt");
          scan = new Scanner(f);
        }
        catch(IOException e) {
        }
        ArrayList<Integer> x1 = new ArrayList<Integer>();
        while(scan.hasNext())
          x1.add(scan.nextInt());
        
        int n = (int) StrictMath.sqrt(x1.size())+1; //n++;
        int[][] M = new int[n][n]; int k=0;
        
        for(int i=1;i<n;i++) {
          for(int j=1;j<n;j++) {
            M[i][j] = x1.get(k); k++;
          } 
        } 
               
        Long start = System.nanoTime();
        ArrayList<Integer> point = new ArrayList<Integer>(); //menyimpan point yang belum di singgahi
        for(int i=1;i<n;i++) point.add(i); //inisialisasi point
        ArrayList<Integer> result = new ArrayList<Integer>(); 
        ArrayList<Simpul> A = new ArrayList<Simpul>(); 

        //inisialisasi
        int num = 1,//menyimpan jumlah simpul
        totalcost = 0;//menyimpan total biaya
        Simpul s = new Simpul(n,1);
        s.CopyM(M); //asumsi ukuran matriks selalu sama
        s.ReduksiM(); 
        s.cost = s.r;
        A.add(s);       
       
        while(point.size()>0) {
          int i = A.get(0).no;
          result.add(i);
          int ii = 0;
          while(point.get(ii)!=i) ii++;
          point.remove(point.get(ii));
          for(int p:point) {
            Simpul a = A.get(0).CostAnak(p); //hitung 
            int j = 0;
            while(j<A.size()) { 
              if(A.get(j).cost<=a.cost)
                j++;
              else break;
            }
            A.add(j,a);
          } 
          totalcost = A.get(0).cost; 
          A.remove(A.get(0));
          num++;
        }
        Long end = System.nanoTime();
        System.out.println("\nRute terpendek : ");
        result.stream().forEach((i) -> {
            System.out.print(i + " ");
        });
        System.out.println("\nTotal cost : "+totalcost);
        num+=A.size()-1;
        System.out.println("\nBanyak simpul : "+num);
        System.out.println("Waktu eksekusi : "+(-start+end)/100000+ " ms");
       
        //BIKIN GRAPH
        Graph g=new SingleGraph("test");
        //UNTUK MENGATASI GARIS YANG TERTUMPUK
         System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        //g.addAttribute("ui.quality");
        //g.addAttribute("ui.antialias");    
        //CSS STYLE SHEET, Disain nya disini PEMANFAATANNYA DIBAWAH
        String styleSheet="node {"+
            " fill-color: black;"+
            " size: 40px;"+
            " stroke-mode: plain;"+
            " stroke-color: black;"+
            " stroke-width: 1px;text-size:30;text-color:blue;"+
            "}"+//NODE
                
            "node.important {"+
            " fill-color: red;"+
            " size: 20px;"+
            "}"+//NODE dengan kelas khusus
                
            "edge{fill-color: blue;text-size:15; }"+//EDGE GLOBAL
                
            "edge.target{text-size:25;text-color:red;arrow-size: 10px, 10px;"+
            " fill-color: red;"+//EDGE KHUSUS
            "}";
        //g manfaatin CSS STYLE SHEET
        g.addAttribute("ui.stylesheet", styleSheet);
        //Membuat N NODE
        //NODE GLOBAL 
        n-=1;
        for(int i=0;i<n;i++){
            //NAMBAH NODE g.addNode("A");
            g.addNode(""+(i+1));
            //AMBIL NODE BUAT DITAMBAH ATRIBUT
            Node a=g.getNode(""+(i+1));
            //Semisal mau diubah ke huruf A 
           
            a.addAttribute("ui.label", ""+(i+1));
        }
        
         //EDGE GLOBAL
        Edge x;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i!=j){
                    //BIKIN SISI PAKE PANAH, KALO GA MAU PANAH HAPUS TRUE nya
                    x=g.addEdge(""+(i+1)+""+(j+1),""+(i+1),""+(j+1),true);
                    //HABIS BIKIN, KALO MAU NAMBAH ATRIBUT, DI GET DULU
                    //Edge xi=g.getEdge("AB"));
                    Edge xi=g.getEdge(""+(i+1)+""+(j+1));
                    //INI BIAR GARIS YANG NUMPUK TULISANNYA GA NUMPUK, MISAH
                    if(i<j) xi.setAttribute("ui.style", "text-offset: +15;"); 
                    else xi.setAttribute("ui.style", "text-offset: -15;");
                    //KASIH NAMA DI SISI
                    xi.addAttribute("ui.label", ""+M[i+1][j+1]);               
                }
            }
        }
        //mewarnai edge yang menjadi jalur terpendek
        while(result.size()>1) {
            Edge xi=g.getEdge(""+result.get(0)+""+result.get(1));
            xi.addAttribute("ui.class","target");
            result.remove(result.get(0));            
        }
        //NAMPILIN YEEEEYYYYY
        g.display();
    
    }   
}
