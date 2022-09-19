package http;

import http.entity.Role;

import java.util.HashSet;
import java.util.Set;

public abstract class Servlet {

    private final Set<Role> roles = new HashSet<>();

    public void get(HttpRequest request, HttpResponse response) {}

    public void post(HttpRequest request, HttpResponse response) {}

    public void delete(HttpRequest request, HttpResponse response) {}

    public void put(HttpRequest request, HttpResponse response) {}

    public void patch(HttpRequest request, HttpResponse response) {}

    public final void addRole(Role role) {
        roles.add(role);
    }

    public final void addRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }

    public final boolean containsRole(Role role) {
        return roles.contains(role);
    }

    public final void removeRole(Role role) {
        roles.remove(role);
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
