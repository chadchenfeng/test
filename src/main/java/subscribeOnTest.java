

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;



public class subscribeOnTest {

	public static void main(String[] args) throws InterruptedException {
		List<student> a=new ArrayList<student>();
		List<String> zhangsanlist=new ArrayList<String>();
		zhangsanlist.add("yuwen");
		zhangsanlist.add("shuxue");
		zhangsanlist.add("yinyu");
		List<String> lishilist=new ArrayList<String>();
		lishilist.add("wuli");
		lishilist.add("huaxue");
		lishilist.add("shengwu");
		a.add(new student("zhangsan",zhangsanlist));
		a.add(new student("lishi",lishilist));
		Observable.from(a)
		.map(student->{
			System.out.println(student.getName()+" 所在线程："+Thread.currentThread().getName());
			return student;
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.io())
		.flatMap(student->{
			System.out.println(student.getName()+" subscribeOn后所在线程："+Thread.currentThread().getName());
			return Observable.from(student.getClassname());
		})
		.map(classname->{
			System.out.println("课程名称："+classname);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "课程："+classname;
		})
		.subscribe(s-> {
			System.out.println("onnext:"+s+"所在线程"+Thread.currentThread().getName());
		});
		Thread.sleep(10000);
	}

}

class student{
	private String name;
	private List<String> classname;
	public student(String name, List<String> classname) {
		super();
		this.name = name;
		this.classname = classname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getClassname() {
		return classname;
	}
	public void setClassname(List<String> classname) {
		this.classname = classname;
	}
	
	
	
}
