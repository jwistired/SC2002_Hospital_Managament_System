import java.io.Serializable;

public class Professor implements Serializable {
	private String name ;
	private String email ;
	private int contact ;

	public Professor(String n, String e, int c)  {
		name = n ;
		email = e ;
		contact = c ;
	}
	public String getName() { return name ; }
	public int getContact() { return contact ; }
	public String getEmail() { return email ; }

	public boolean equals(Object o) {
		if (o instanceof Professor) {
			Professor p = (Professor)o;
			return (getName().equals(p.getName()));
		}
		return false;
	}
}