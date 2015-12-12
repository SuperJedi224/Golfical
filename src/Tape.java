public class Tape{
	static class Cell{
		Tape.Cell prev,next;
		int value=0;
		Cell(){};
		Cell(Tape.Cell a,Tape.Cell b){prev=a;next=b;}
		Tape.Cell getNext(){
			return next==null?next=new Cell(this,null):next;
		}
		Tape.Cell getPrevious(){
			return prev==null?prev=new Cell(null,this):prev;
		}
	}
	Tape.Cell currentCell=new Cell();
	void spliceBefore(){
		Tape.Cell a=new Cell(currentCell.getPrevious(),currentCell);
		currentCell.getPrevious().next=a;
		currentCell.prev=a;
	}
	void spliceAfter(){
		Tape.Cell a=new Cell(currentCell,currentCell.getNext());
		currentCell.getNext().prev=a;
		currentCell.next=a;
	}
	void goLeft(){
		currentCell=currentCell.getPrevious();
	}
	void goRight(){
		currentCell=currentCell.getNext();
	}
	void spliceOutRight(){
		currentCell.getPrevious().next=currentCell.getNext();
		currentCell.getNext().prev=currentCell.getPrevious();
		goRight();
	}
	void spliceOutLeft(){
		currentCell.getPrevious().next=currentCell.getNext();
		currentCell.getNext().prev=currentCell.getPrevious();
		goLeft();
	}
	void set(int v){
		currentCell.value=v;
	}
	void incr(int v){
		currentCell.value+=v;
	}
	int get(){
		return currentCell.value;
	}
}