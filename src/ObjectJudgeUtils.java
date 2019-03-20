import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ObjectJudgeUtils {
	
	public static boolean isNull(Object object){
		if(object != null)
			return false;
		return true;
	}
	
	public static <E> boolean isNull(Collection<E> collection){
		if(collection != null)
			return false;
		return true;
	}
	
	public static <K,V> boolean isNull(Map<K, V> map){
		if(map != null)
			return false;
		return true;
	}
	
	public static boolean isNull(Object[] objects){
		if(objects != null && objects.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(byte[] b){
		if(b != null && b.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(short[] s){
		if(s != null && s.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(int[] i){
		if(i != null && i.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(long[] l){
		if(l != null && l.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(float[] f){
		if(f != null & f.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(double[] d){
		if(d != null && d.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNull(char[] c){
		if(c != null && c.length > 0)
			return false;
		return true;
	}
	
	public static boolean isNotEmpty(Object object){
		if(object != null)
			return true;
		return false;
	}
	public static <E> boolean isNotEmpty(Collection<E> collection){
		if(collection != null){
			Iterator<E> iterator = collection.iterator();
			while(iterator.hasNext()){
				if(iterator.next() != null){
					return true;
				}
			}
		}
		return false;
	}
	public static <K,V> boolean isNotEmpty(Map<K, V> map){
		if(map != null)
			return !map.isEmpty();
		return false;
	}
	
	public static boolean isNotEmpty(Object[] objects){
		if(objects != null && objects.length > 0)
			for(int i = 0; i < objects.length; i++){
				if(objects[i] != null)
					return true;
			}
		return false;
	}
	
	public static boolean isNotEmpty(byte[] b){
		return isNotEmpty(b);
	}
	
	public static boolean isNotEmpty(int[] i){
		return isNotEmpty(i);
	}
	
	public static boolean isNotEmpty(short[] s){
		return isNotEmpty(s);
	}
	public static boolean isNotEmpty(long[] l){
		return isNotEmpty(l);
	}
	public static boolean isNotEmpty(float[] f){
		return isNotEmpty(f);
	}
	public static boolean isNotEmpty(double[] d){
		return isNotEmpty(d);
	}
	public static boolean isNotEmpty(char[] c){
		return isNotEmpty(c);
	}
}
