import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Golfical{
	private static int gcd(int a, int b)
	{
	    while (b > 0)
	    {
	        int temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}
	private static int lcm(int a, int b)
	{
	    return a * (b / gcd(a, b));
	}

	static int REG=0;
	static final int N=0,E=1,S=2,W=3;
	static int rev(int d){
		return (d+2)%4;
	}
	static int cw(int d){
		return (d+1)%4;
	}
	static int ccw(int d){
		return (d+3)%4;
	}
	static Tape theTape=new Tape();
	static Random r=new Random();
	static Stack<Integer> stack=new Stack<Integer>();
	public static void main(String[]args) throws IOException{
		String fname;
		if(args.length>0){
			fname=args[0];
		}else{
			fname=new Scanner(System.in).nextLine();
		}
		BufferedImage i=ImageIO.read(new File(fname));
		int x=0,y=0,d=E;
		
		while(true){
			String s=String.format("%06X",0xFFFFF&i.getRGB(x,y));
			if(s.startsWith("00"))theTape.set(Integer.parseInt(s.substring(2),16));
			if(s.startsWith("01"))theTape.set(-Integer.parseInt(s.substring(2),16));
			if(s.startsWith("02"))theTape.incr(Integer.parseInt(s.substring(2),16));
			if(s.startsWith("03"))theTape.incr(-Integer.parseInt(s.substring(2),16));
			if(s.startsWith("04")){
				int n=Integer.parseInt(s.substring(2),16);
				for(int j=0;j<n;j++){
					theTape.goRight();
				}
			}
			if(s.startsWith("05")){
				int n=Integer.parseInt(s.substring(2),16);
				for(int j=0;j<n;j++){
					theTape.goLeft();
				}
			}
			if(s.startsWith("06")){
				int n=Integer.parseInt(s.substring(2),16);
				Tape.Cell c=theTape.currentCell;
				for(int j=0;j<n;j++){
					c=c.getNext();
				}
				c.value=theTape.get();
			}
			if(s.startsWith("07")){
				int n=Integer.parseInt(s.substring(2),16);
				Tape.Cell c=theTape.currentCell;
				for(int j=0;j<n;j++){
					c=c.getPrevious();
				}
				c.value=theTape.get();
			}
			if(s.startsWith("08")){
				int n=Integer.parseInt(s.substring(2),16);
				Tape.Cell c=theTape.currentCell;
				for(int j=0;j<n;j++){
					c=c.getNext();
					c.value=theTape.get();
				}
			}
			if(s.startsWith("09")){
				int n=Integer.parseInt(s.substring(2),16);
				Tape.Cell c=theTape.currentCell;
				for(int j=0;j<n;j++){
					c=c.getPrevious();
					c.value=theTape.get();
				}
			}
			if(s.equals("0A0000")){
				theTape.set(new Scanner(System.in).nextInt());
			}
			if(s.equals("0A0001")){
				int j=System.in.read();
				theTape.set(j<0?0:j);
			}
			if(s.equals("0A0100")){
				System.out.println(theTape.get());
			}
			if(s.equals("0A0101")){
				System.out.print((char)theTape.get());
			}
			if(s.startsWith("0B000")){
				d=s.charAt(5)-48;
			}
			if(s.equals("0B0100")){
				d=cw(d);
			}
			if(s.equals("0B0101")){
				d=ccw(d);
			}
			if(s.equals("0B0102")){
				d=rev(d);
			}
			if(s.equals("0B0200")&&theTape.get()==0){
				d=ccw(d);
			}
			if(s.equals("0B0201")&&theTape.get()!=0){
				d=ccw(d);
			}
			if(s.equals("0B0202")&&theTape.get()==0){
				d=cw(d);
			}
			if(s.equals("0B0203")&&theTape.get()!=0){
				d=cw(d);
			}
			if(s.equals("0B0300")&&theTape.get()==theTape.currentCell.getNext().value){
				d=cw(d);
			}
			if(s.equals("0B0301")&&theTape.get()<theTape.currentCell.getNext().value){
				d=cw(d);
			}
			if(s.equals("0B0302")&&theTape.get()<=theTape.currentCell.getNext().value){
				d=cw(d);
			}
			if(s.equals("0B0400")&&REG==0){
				d=ccw(d);
			}
			if(s.equals("0B0401")&&REG==0){
				d=cw(d);
			}
			if(s.equals("0B0402")&&REG!=0){
				d=ccw(d);
			}
			if(s.equals("0B0403")&&REG!=0){
				d=cw(d);
			}
			if(s.equals("0C0000"))stack.push(theTape.get());
			if(s.equals("0C0001"))theTape.set(stack.pop());
			if(s.equals("0C0002"))REG=stack.pop();
			if(s.equals("0C0003"))theTape.set(stack.peek());
			if(s.equals("0C0004"))REG=stack.peek();
			if(s.equals("0C0005"))stack.pop();
			if(s.equals("0C0006"))stack.push(stack.peek());
			if(s.equals("0C0007")){
				List<Integer>l=new ArrayList<>();
				while(!stack.isEmpty())l.add(stack.pop());
				for(int j:l)stack.push(j);
			}
			if(s.equals("0C0008")){
				Tape.Cell c=theTape.currentCell;
				while(!stack.isEmpty()){
					c.value=stack.pop();
					c=c.getNext();
				}
			}
			if(s.equals("0C0009"))theTape.set(stack.size());
			if(s.equals("0C000A")&&stack.isEmpty())d=cw(d);
			if(s.equals("0D0000"))theTape.set(r.nextInt());
			if(s.equals("0D0001"))theTape.set(r.nextInt(theTape.get()));
			if(s.equals("0D0002"))r=new Random(theTape.get());
			if(s.equals("0E0000"))theTape.currentCell.value+=theTape.currentCell.getNext().value;
			if(s.equals("0E0001"))theTape.currentCell.value-=theTape.currentCell.getNext().value;
			if(s.equals("0E0002"))theTape.currentCell.value*=theTape.currentCell.getNext().value;
			if(s.equals("0E0003"))theTape.currentCell.value/=theTape.currentCell.getNext().value;
			if(s.equals("0E0004"))theTape.set(gcd(theTape.currentCell.value,theTape.currentCell.getNext().value));
			if(s.equals("0E0005"))theTape.set(lcm(theTape.currentCell.value,theTape.currentCell.getNext().value));
			if(s.equals("0E0100"))theTape.set(theTape.currentCell.value|theTape.currentCell.getNext().value);
			if(s.equals("0E0101"))theTape.set(theTape.currentCell.value&theTape.currentCell.getNext().value);
			if(s.equals("0E0102"))theTape.set(theTape.currentCell.value^theTape.currentCell.getNext().value);
			if(s.equals("0E0103")){
				String q=Integer.toBinaryString(theTape.get());
				while(q.length()<32){q="0"+q;}
				char[]k=q.toCharArray();
				for(int j=theTape.currentCell.getNext().value;j>0;j--){
					for(int l=0;l<31;l--){
						k[l]=k[l+1];
					}
					k[31]='0';
				}
				theTape.set(Integer.parseUnsignedInt(String.valueOf(k),2));
			}
			if(s.equals("0E0104")){
				String q=Integer.toBinaryString(theTape.get());
				while(q.length()<32){q="0"+q;}
				char[]k=q.toCharArray();
				for(int j=theTape.currentCell.getNext().value;j>0;j--){
					for(int l=31;l>0;l--){
						k[l]=k[l-1];
					}
					k[0]='0';
				}
				theTape.set(Integer.parseUnsignedInt(String.valueOf(k),2));
			}
			if(s.equals("0E0105")){
				String q=Integer.toBinaryString(theTape.get());
				while(q.length()<32){q="0"+q;}
				char[]k=q.toCharArray();
				for(int j=theTape.currentCell.getNext().value;j>0;j--){
					char v=k[0];
					for(int l=0;l<31;l--){
						k[l]=k[l+1];
					}
					k[31]=v;
				}
				theTape.set(Integer.parseUnsignedInt(String.valueOf(k),2));
			}
			if(s.equals("0E0106")){
				String q=Integer.toBinaryString(theTape.get());
				while(q.length()<32){q="0"+q;}
				char[]k=q.toCharArray();
				for(int j=theTape.currentCell.getNext().value;j>0;j--){
					char v=k[31];
					for(int l=31;l>0;l--){
						k[l]=k[l-1];
					}
					k[0]=v;
				}
				theTape.set(Integer.parseUnsignedInt(String.valueOf(k),2));
			}
			if(s.equals("0E0200"))theTape.set(-theTape.get());
			if(s.equals("0E0201"))theTape.set(~theTape.get());
			if(s.equals("0F0000"))theTape.spliceOutLeft();
			if(s.equals("0F0001"))theTape.spliceOutRight();
			if(s.equals("0F0002"))theTape.spliceBefore();
			if(s.equals("0F0003"))theTape.spliceAfter();
			if(s.equals("100000"))REG=theTape.get();
			if(s.equals("100001")){
				int a=theTape.get();
				theTape.set(REG);
				REG=a;
			}
			if(s.equals("100002"))stack.push(REG);
			if(s.equals("100003"))REG=0;
			if(d==N)y--;
			if(d==S)y++;
			if(d==E)x++;
			if(d==W)x--;
			if(x<0||x>=i.getWidth()||y<0||y>=i.getHeight())break;
		}
	}
}