package trax.types;

import java.util.ArrayList;

public class TraxArrayList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	
	private int startWithIndex;
	
	
	 private Class<E> type;
     public TraxArrayList(Class<E> cls)
     {
    	this();
        type= cls;
     }
     
     public Class<E> getType(){return type;}
	
	
	public TraxArrayList() {
		super();
		startWithIndex = 1;
	}
	
	public TraxArrayList(int startWithIndex) {
		super();
		this.startWithIndex = startWithIndex;
	}
	
	@Override
	public int indexOf(Object o) {
		int r = super.indexOf(o);
		if (r >= 0) r += startWithIndex;
		return r;
	}

	@Override
	public int lastIndexOf(Object o) {
		int r = super.lastIndexOf(o);
		if (r >= 0) r += startWithIndex;
		return super.lastIndexOf(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		try {
			int i = index - startWithIndex;
			if (i >= size()) set(index, null);
			E e = super.get(index - startWithIndex);
			if (e == null) {
				try {
					if( getType()!= null )
					{	
						if(	getType().equals(String.class))
							e = (E) "";
						else
							e = (E) Class.forName(getType().getName()).newInstance();
					}
					else
						e = (E) "";
				} catch (Exception ex) {
					//Do Nothing
				}
			}
			return e;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public E set(int index, E element) {
		int i = index - startWithIndex;
		if (size() <= i) {
			int size = size();
			for (int ni = size; ni <= i; ni++) {
				add(null);
			}
		}
		return super.set(i, element);
	}

	public int getStartWithIndex() {
		return startWithIndex;
	}

	public void setStartWithIndex(int startWithIndex) {
		this.startWithIndex = startWithIndex;
	}
	
}
