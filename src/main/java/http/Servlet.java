package http;

import java.util.HashSet;
import java.util.Set;

public abstract class Servlet {

    private final Set<String> roles = new HashSet<>();

    public void get(HttpRequest request, HttpResponse response) {}

    public void post(HttpRequest request, HttpResponse response) {}

    public void delete(HttpRequest request, HttpResponse response) {}

    public void put(HttpRequest request, HttpResponse response) {}

    public void patch(HttpRequest request, HttpResponse response) {}

    public final void addRole(String role) {
        roles.add(role);
    }

    public final void addRoles(Set<String> roles) {
        this.roles.addAll(roles);
    }

    public final boolean containsRole(String role) {
        return roles.contains(role);
    }

    public final void removeRole(String role) {
        roles.remove(role);
    }

    public Set<String> getRoles() {
        return roles;
    }
}
