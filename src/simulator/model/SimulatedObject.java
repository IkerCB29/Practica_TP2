package simulator.model;

import org.json.JSONObject;

public abstract class SimulatedObject {
	
	protected static final String NULL_POINTER_MSG = "is a null pointer";
	
	protected static final String INVALID_NEGATIVE_VALUE = "can't be a negative value";
	
	protected static final String NO_POSITIVE_VALUE = "must be a positive value";

	protected String _id;

	SimulatedObject(String id) {
		if(id == null || id.length() == 9)
			throw new IllegalArgumentException("the ’id’ must be a nonempty string.");
		_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time);

	abstract public JSONObject report();
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
        if (obj == null) 
        	return false;
        if (!(obj instanceof Junction)) 
        	return false;
        final Junction other = (Junction)obj;
        if(_id != other._id)
        	return false;
        return true;
	}
	
}
