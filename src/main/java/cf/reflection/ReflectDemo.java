package cf.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Student{
	public Student() {
		System.out.println("student 构造函数");
	}
	
	private String name;
	private Integer age;
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return "name="+name+" age="+age;
	}
	
}
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		//Class<?> cls = Class.forName("cf.reflection.Student");
		Class<?> cls=Student.class;
		Method method = cls.getMethod("setName", String.class);
		Method method2 = cls.getMethod("setAge", Integer.class);
		Field[] declaredFields = cls.getDeclaredFields();
		for(Field field:declaredFields) {
			System.out.println(field.getName()+"---"+field.getType().getSimpleName());
		}
		
		Object obj=cls.newInstance();
		method.invoke(obj, "chenfeng");
		method2.invoke(obj, 28);
		System.out.println(obj);
	}
}
