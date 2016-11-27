package json;
import java.util.*;

public class JSON {
    private Object _json;
    private JSON() {}
    public static JSON list() {
        JSON result = new JSON();
        result._json = new ArrayList<String>();
        return result;
    }
    public static JSON object() {
        JSON result = new JSON();
        result._json = new HashMap<String,String>();
        return result;
    }
    private boolean isList() {
        return this._json instanceof List;
    }
    public void insert(String value) {
        if (this.isList()) {
            ((List<String>)this._json).add("\"" + value + "\"");
        }
    }
    public void insert(int value) {
        if (this.isList()) {
            ((List<String>)this._json).add(Integer.toString(value));
        }
    }
    public void insert(JSON value) {
        if (this.isList()) {
            ((List<String>)this._json).add(value.toString());
        }
    }
    public void insert(String key, int value) {
        if (!this.isList()) {
            ((Map<String,String>)this._json).put(key, Integer.toString(value));
        }
    }
    public void insert(String key, String value) {
        if (!this.isList()) {
            ((Map<String,String>)this._json).put(key, "\"" + value + "\"");
        }
    }
    public void insert(String key, JSON value) {
        if (!this.isList()) {
            ((Map<String,String>)this._json).put(key, value.toString());
        }
    }
    public String toString() {
        StringBuffer result = new StringBuffer();
        if (this.isList()) {
            Iterator it = ((List<String>)this._json).iterator();
            result.append("[");
            while(it.hasNext()) {
                result.append(it.next());
                if (it.hasNext()) {
                    result.append(",");
                }
            }
            result.append("]");
        } else {
            result.append("{");
            Map.Entry entry;
            Iterator it = ((Map<String,String>)this._json).entrySet().iterator();
            while (it.hasNext()) {
                entry = (Map.Entry)it.next();
                result.append("\"" + entry.getKey() + "\":" + entry.getValue());
                if (it.hasNext()) {
                    result.append(",");
                }
            }
            result.append("}");
        }
        return result.toString();
    }
} 
