import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;


public class Encoder{
static List<String>col=Arrays.asList(
		"FFFFFF","0A0000","0A0001","0A0100","0A0101",
		"0A0200","0B0000","0B0001","0B0002","0B0003",
		"0B0100","0B0101","0B0102","0B0200","0B0201",
		"0B0202","0B0203","0B0300","0B0301","0B0302",
		"0B0400","0B0401","0B0402","0B0403","0C0000",
		"0C0001","0C0002","0C0003","0C0004","0C0005",
		"0C0006","0C0007","0C0008","0C0009","0C000A",
		"0D0000","0D0001","0D0002","0E0000","0E0001",
		"0E0002","0E0003","0E0004","0E0005","0E0100",
		"0E0101","0E0102","0E0103","0E0104","0E0105",
		"0E0106","0E0200","0E0201","0E0300","0E0301",
		"0F0000","0F0001","0F0002","0F0003","100000",
		"100001","100002","100003","0E0006"
);
static int ct=0;
static void write(int b,OutputStream p) throws Exception{
	p.write(b);
	ct++;
}
static void write(byte[]b,OutputStream p) throws Exception{
	p.write(b);
	ct+=b.length;
}
public static void to(File src,String name) throws Exception{
	BufferedImage img=ImageIO.read(src);
	OutputStream p=new FileOutputStream(new File(name));
	int t=4096*img.getWidth()+img.getHeight();
	String s=Integer.toBinaryString(t);
	while(s.length()<24)s="0"+s;
	System.out.println(s);
	write(new byte[]{(byte)Integer.parseUnsignedInt(s.substring(0,8),2),(byte)Integer.parseUnsignedInt(s.substring(8,16),2),(byte)Integer.parseUnsignedInt(s.substring(16),2)},p);
	for(int x=0;x<img.getWidth();x++){
		for(int y=0;y<img.getHeight();y++){
			Color c=new Color(img.getRGB(x,y));
			if(c.getRed()<10){
				if(c.getGreen()==0){
					write(2*c.getRed(),p);
					write(c.getBlue(),p);
				}else{
					write(2*c.getRed()+1,p);
					write(c.getBlue(),p);
					write(c.getBlue(),p);
				}
			}else{
				String k=String.format("%06x",0xFFFFFF&c.getRGB()).toUpperCase();
				if(col.indexOf(k)==-1)k=col.get(0);
				write(20+col.indexOf(k),p);
			}
		}
	}
	System.out.println(ct);
}
public static void from(File f,String out) throws Exception{
	InputStream p=new FileInputStream(f);
	String q=String.format("%02x%02x%02x",p.read(),p.read(),p.read());
	BufferedImage img=new BufferedImage(Integer.parseInt(q.substring(0,3),16),Integer.parseInt(q.substring(3),16),BufferedImage.TYPE_INT_RGB);
	for(int x=0;x<img.getWidth();x++){
		for(int y=0;y<img.getHeight();y++){
			int i=p.read();
			if(i<19){
				int r=i/2;
				int g=0;
				if(i%2==1)g=p.read();
				int b=p.read();
				img.setRGB(x, y, new Color(r,g,b).getRGB());
			}else{
				String k=col.get(i-20);
				img.setRGB(x,y,new Color(Integer.parseInt(k.substring(0,2),16),Integer.parseInt(k.substring(2,4),16),Integer.parseInt(k.substring(4),16)).getRGB());
			}
		}
	}
	ImageIO.write(img,"png",new File(out));
}
public static void main(String[]a) throws Exception{
	if(a[0].equals("e")){
		to(new File(a[1]),a[2]);
	}else if(a[0].equals("d")){
		from(new File(a[1]),a[2]);
	}else{
		System.out.println("Option "+a[0]+" is invalid.");
	}
}
}
