
import javax.swing.JTable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andriansyah
 */
public class KNN {
    
    int count[] = new int[5];
    public String hitungData(int k, int jumlah, JTable tabel, rgb img) {
        int rr[]=new int[jumlah];
        int gg[]=new int[jumlah];
        int bb[]=new int[jumlah];
        String hasil = null;
        for (int i=0;i<jumlah;i++){
            rr[i]=Integer.parseInt(tabel.getValueAt(i, 1).toString());
            gg[i]=Integer.parseInt(tabel.getValueAt(i, 2).toString());
            bb[i]=Integer.parseInt(tabel.getValueAt(i, 3).toString());
        }
        float euc[]=new float[jumlah];
        float id1=0, id2=0, id3=0, id4=0, id5=0,tetangga1=0, tetangga2=0, tetangga3=0, tetangga4=0, tetangga5=0,temp=500;
        for (int i=0;i<jumlah;i++){
            euc[i]=(float) Math.sqrt(Math.pow((rr[i]-img.nilair),2)+Math.pow((gg[i]-img.nilaig),2)+Math.pow((bb[i]-img.nilaib),2));
//            System.out.println("Jarak dengan data latih ke-"+(i+1)+" = "+euc[i]);
            if(k==1){
                if(euc[i] < temp){
                    temp = euc[i];
                    tetangga1 = euc[i];
                    id1 = i;
                }
            }
            else if(k==2){
                if(euc[i] < temp) {
                    temp=euc[i];
                    tetangga2 = tetangga1;
                    tetangga1 = euc[i];
                    id1=i;
                }
                if ((euc[i] < tetangga2 || tetangga2 == tetangga1) && euc[i] != tetangga1){
                    tetangga2 = euc[i];
                    id2=i;
                }
            }
            else if(k==3){
                if(euc[i] < temp){
                    temp=euc[i];
                    tetangga3=tetangga1;
                    tetangga2=tetangga1;
                    tetangga1 = euc[i];
                    id1 = i;
                }
                if((euc[i]<tetangga2 || tetangga2 == tetangga1)&& euc[i] != tetangga1){
                    tetangga2 = euc[i];
                    id2 = i;
                }
                if((euc[i] < tetangga3 || tetangga3 == tetangga2)&& euc[i]!=tetangga1 && euc[i]!=tetangga2){
                    tetangga3 = euc[i];
                    id3 = i;
                }
            }
            else if(k==4){
                if(euc[i] < temp){
                    temp=euc[i];
                    tetangga4=tetangga1;
                    tetangga3=tetangga1;
                    tetangga2=tetangga1;
                    tetangga1 = euc[i];
                    id1 = i;
                }
                if((euc[i]<tetangga2 || tetangga2 == tetangga1)&& euc[i] != tetangga1){
                    tetangga2 = euc[i];
                    id2 = i;
                }
                if((euc[i] < tetangga3 || tetangga3 == tetangga2)&& euc[i]!=tetangga1 && euc[i]!=tetangga2){
                    tetangga3 = euc[i];
                    id3 = i;
                }
                if((euc[i] < tetangga4 || tetangga4 == tetangga3)&& euc[i]!=tetangga1 && euc[i]!=tetangga2 && euc[i]!=tetangga3){
                    tetangga4 = euc[i];
                    id4 = i;
                }
            }
            
            else if(k==5){
                if(euc[i] < temp){
                    temp=euc[i];
                    tetangga5=tetangga1;
                    tetangga4=tetangga1;
                    tetangga3=tetangga1;
                    tetangga2=tetangga1;
                    tetangga1 = euc[i];
                    id1 = i;
                }
                if((euc[i]<tetangga2 || tetangga2 == tetangga1)&& euc[i] != tetangga1){
                    tetangga2 = euc[i];
                    id2 = i;
                }
                if((euc[i] < tetangga3 || tetangga3 == tetangga2)&& euc[i]!=tetangga1 && euc[i]!=tetangga2){
                    tetangga3 = euc[i];
                    id3 = i;
                }
                if((euc[i] < tetangga4 || tetangga4 == tetangga3)&& euc[i]!=tetangga1 && euc[i]!=tetangga2 && euc[i]!=tetangga3){
                    tetangga4 = euc[i];
                    id4 = i;
                }
                if((euc[i] < tetangga5 || tetangga5 == tetangga4)&& euc[i]!=tetangga1 && euc[i]!=tetangga2 && euc[i]!=tetangga3 && euc[i]!=tetangga4){
                    tetangga5 = euc[i];
                    id5 = i;
                }
            }
        }
        if(k==1){
            System.out.println(tetangga1);
            System.out.println((1+id1));
            double t1 = 1/Math.pow(tetangga1, 2);
            hasil = tabel.getValueAt((int) id1, 4).toString();
//            System.out.println(hasil);
        }
        else if(k==2){
            System.out.println(tetangga1+"\t"+tetangga2);
            System.out.println((1+id1)+"\t\t"+(1+id2));
            double t1 = 1/Math.pow(tetangga1, 2);
            double t2 = 1/Math.pow(tetangga2, 2);
            
            if(t1>t2){
                hasil = tabel.getValueAt((int)id1, 4).toString();               
            }
            else{
                hasil = tabel.getValueAt((int)id2, 4).toString();
            }
        }
        else if(k==3){
            System.out.println(tetangga1+"\t"+tetangga2+"\t"+tetangga3);
            System.out.println((1+id1)+"\t\t"+(1+id2)+"\t\t"+(1+id3));
            double t1 = 1/Math.pow(tetangga1, 2);
            double t2 = 1/Math.pow(tetangga2, 2);
            double t3 = 1/Math.pow(tetangga3, 2);
            String kelas1, kelas2, kelas3;
            kelas1 = tabel.getValueAt((int)id1, 4).toString();
            kelas2 = tabel.getValueAt((int)id2, 4).toString(); 
            kelas3 = tabel.getValueAt((int)id3, 4).toString();
            System.out.println(kelas1+" "+kelas2+" "+kelas3);
            count[0] = 0;
            count[1] = 0;
            count[2] = 0;
            count[3] = 0;
            count[4] = 0;
            getCount(kelas1);
            getCount(kelas2);
            getCount(kelas3);
            int id =0;
            if(count[0]==1&&count[1]==1&&count[2]==1&&count[3]==1&&count[4]==1){
                double min = 500.0;
                double jarak[] = new double[3];
                jarak[0] = t1;
                jarak[1] = t2;
                jarak[2] = t3;
                for(int i=0;i<jarak.length;i++){
                    if(min>jarak[i]){
                        min=jarak[i];
                        id = i;
                    }
                }
            }
            else{
                int max = 0;
                for(int i=0;i<count.length;i++){
                    if(max<count[i]){
                        max = count[i];
                        id = i;
                    }
                }
            }
            if(id==0){
                hasil = "mildew";
            }else if(id==1){
                hasil = "cvpd";
            }else if(id==2){
                hasil = "jelaga";
            }else if(id==3){
                hasil = "defisiensi";
            }else if(id==4){
                hasil = "sehat";
            }
        }
        else if(k==4){
            System.out.println(tetangga1+"\t"+tetangga2+"\t"+tetangga3+"\t"+tetangga4);
            System.out.println((1+id1)+"\t\t"+(1+id2)+"\t\t"+(1+id3)+"\t\t"+(1+id4));
            double t1 = 1/Math.pow(tetangga1, 2);
            double t2 = 1/Math.pow(tetangga2, 2);
            double t3 = 1/Math.pow(tetangga3, 2);
            double t4 = 1/Math.pow(tetangga4, 2);
            String kelas1, kelas2, kelas3, kelas4;
            kelas1 = tabel.getValueAt((int)id1, 4).toString();
            kelas2 = tabel.getValueAt((int)id2, 4).toString(); 
            kelas3 = tabel.getValueAt((int)id3, 4).toString(); 
            kelas4 = tabel.getValueAt((int)id4, 4).toString();
            System.out.println(kelas1+" "+kelas2+" "+kelas3+" "+kelas4);
            count[0] = 0;
            count[1] = 0;
            count[2] = 0;
            count[3] = 0;
            count[4] = 0;
            getCount(kelas1);
            getCount(kelas2);
            getCount(kelas3);
            getCount(kelas4);
            int id =0;
            if(count[0]==1&&count[1]==1&&count[2]==1&&count[3]==1&&count[4]==1){
                double min = 500.0;
                double jarak[] = new double[4];
                jarak[0] = t1;
                jarak[1] = t2;
                jarak[2] = t3;
                jarak[3] = t4;
                for(int i=0;i<jarak.length;i++){
                    if(min>jarak[i]){
                        min=jarak[i];
                        id = i;
                    }
                }
            }
            else{
                int max = 0;
                for(int i=0;i<count.length;i++){
                    if(max<count[i]){
                        max = count[i];
                        id = i;
                    }
                }
            }
            if(id==0){
                hasil = "mildew";
            }else if(id==1){
                hasil = "cvpd";
            }else if(id==2){
                hasil = "jelaga";
            }else if(id==3){
                hasil = "defisiensi";
            }else if(id==4){
                hasil = "sehat";
            }
        }
        else if(k==5){
            System.out.println(tetangga1+"\t"+tetangga2+"\t"+tetangga3+"\t"+tetangga4+"\t"+tetangga5);
            System.out.println((1+id1)+"\t\t"+(1+id2)+"\t\t"+(1+id3)+"\t\t"+(1+id4)+"\t\t"+(1+id5));
            double t1 = 1/Math.pow(tetangga1, 2);
            double t2 = 1/Math.pow(tetangga2, 2);
            double t3 = 1/Math.pow(tetangga3, 2);
            double t4 = 1/Math.pow(tetangga4, 2);
            double t5 = 1/Math.pow(tetangga5, 2);
            String kelas1, kelas2, kelas3, kelas4, kelas5;
            kelas1 = tabel.getValueAt((int)id1, 4).toString();
            kelas2 = tabel.getValueAt((int)id2, 4).toString(); 
            kelas3 = tabel.getValueAt((int)id3, 4).toString(); 
            kelas4 = tabel.getValueAt((int)id4, 4).toString(); 
            kelas5 = tabel.getValueAt((int)id5, 4).toString();
            System.out.println(kelas1+" "+kelas2+" "+kelas3+" "+kelas4+" "+kelas5);
            count[0] = 0;
            count[1] = 0;
            count[2] = 0;
            count[3] = 0;
            count[4] = 0;
            getCount(kelas1);
            getCount(kelas2);
            getCount(kelas3);
            getCount(kelas4);
            getCount(kelas5);
            int id =0;
            if(count[0]==1&&count[1]==1&&count[2]==1&&count[3]==1&&count[4]==1){
                double min = 500.0;
                double jarak[] = new double[5];
                jarak[0] = t1;
                jarak[1] = t2;
                jarak[2] = t3;
                jarak[3] = t4;
                jarak[4] = t5;
                for(int i=0;i<jarak.length;i++){
                    if(min>jarak[i]){
                        min=jarak[i];
                        id = i;
                    }
                }
            }
            else{
                int max = 0;
                for(int i=0;i<count.length;i++){
                    if(max<count[i]){
                        max = count[i];
                        id = i;
                    }
                }
            }
            if(id==0){
                hasil = "mildew";
            }else if(id==1){
                hasil = "cvpd";
            }else if(id==2){
                hasil = "jelaga";
            }else if(id==3){
                hasil = "defisiensi";
            }else if(id==4){
                hasil = "sehat";
            }
        }
    return hasil;
    }
    void getCount(String kelas){
        if(kelas.equals("mildew")){
            count[0]++;
        }
        else if(kelas.equals("cvpd")){
            count[1]++;
        }
        else if(kelas.equals("jelaga")){
            count[2]++;
        }else if(kelas.equals("defisiensi")){
            count[3]++;
        }else if(kelas.equals("sehat")){
            count[4]++;
        }
    }
}
