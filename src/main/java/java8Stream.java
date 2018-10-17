import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class java8Stream {

	
	public static void main(String[] args) {
		Students s0=new Students("xxx",100);
		Students s1=new Students("cf",28);
		Students s2=new Students("cj",29);
		Students s3=new Students("czh",2);
		Students s4=new Students("czh",3);
		List<Students> list=new ArrayList<Students>();
		list.add(s0);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		/*Collections.sort(list,new Comparator<Students>() {
			@Override
			public int compare(Students o1, Students o2) {
				return o1.getAge()-o2.getAge();
			}
		});*/
		/*Collections.sort(list, (o1,o2)->{
			return o1.getAge()-o2.getAge();
		});
		for(Students s:list) {
			System.out.println(s.getName()+","+s.getAge());
		}
		*/
		System.out.println("=========java8Stream=======");
		Map<Boolean, List<Students>> collect = list.stream().filter(t->t.getAge()>1)
		.distinct()
		.sorted((o1,o2)->{
			return o1.getAge()-o2.getAge();
		})
		//.map(Students::getAge)
		.collect(Collectors.partitioningBy(u->u.getAge()>18));
		/*for(Students s:collect) {
			System.out.println(s.getName()+","+s.getAge());
		}*/
		System.out.println(collect.toString());
		
	}
	
	
}

class Students{
	private String name;
	private int age;
	public Students(String n,int a) {
		this.name=n;
		this.age=a;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Students other = (Students) obj;
		/*if (age != other.age)
			return false;*/
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
