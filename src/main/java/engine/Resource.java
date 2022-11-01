package engine;

import java.util.HashMap;
import java.util.Map;

public class Resource<T> {

    Map<String, T> resources;

    public Resource() {
        resources = new HashMap<>();
    }

    public void putResource(String resourceName, T resource) {
        resources.put(resourceName, resource);
    }

    public T getResource(String resourceName) {
        T r = resources.get(resourceName);
        if (r == null) {
            System.out.println(resourceName + " resource does not exist");
        }
        return r;
    }
}
