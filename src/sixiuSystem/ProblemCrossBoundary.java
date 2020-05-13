package sixiuSystem;

//异常：问题越界，想要访问的问题编号不存在
public class ProblemCrossBoundary extends Exception{
	static final String msg = "problem cross boundary";
	public ProblemCrossBoundary() {
		super(msg);
	}
}
