

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class rxjavaTest {

	public static void main(String[] args) {
		List<String> org=new ArrayList<String>();
		org.add("1");
		org.add("2");
		org.add("3");
		org.add("4");
		org.add("5");
		int[] a= {1,2,3};
		/*Observable.from(org)
		//Observable.just(a)
		//Observable.from(Arrays.asList(a))
		.flatMap(str->{
			System.out.println(str+"flatMap   "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Observable.from(Arrays.asList(str));
		})
		.doOnSubscribe(()-> {
			System.out.println("doOnSubscribe   "+Thread.currentThread().getName());
		})
		//.observeOn(Schedulers.newThread())
		//.subscribeOn(Schedulers.newThread())
		.flatMap(str->{
			System.out.println(str+"flatMap   "+Thread.currentThread().getName());
			return Observable.just(str);
		})
		.map(str->{
			System.out.println(str+"Map   "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return str+Thread.currentThread().getName()+":";
		})
		.subscribe(str->{
			System.out.println("=================end:"+str+Thread.currentThread().getName());
			System.out.println("\n");
		});*/
	}

}
