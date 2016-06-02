package edu.netcracker.center.web.mvc;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RolesRequestCondition implements RequestCondition<RolesRequestCondition> {

    private final Set<String> roles;

    public RolesRequestCondition(String... roles) {
        this(Arrays.asList(roles));
    }

    private RolesRequestCondition(Collection<String> roles) {
        this.roles = Collections.unmodifiableSet(new HashSet<>(roles));
    }

    @Override
    public RolesRequestCondition combine(RolesRequestCondition other) {
        Set<String> allRoles = new LinkedHashSet<>(this.roles);
        allRoles.addAll(other.roles);
        return new RolesRequestCondition(allRoles);
    }

    @Override
    public RolesRequestCondition getMatchingCondition(HttpServletRequest request) {
//        for (String role : this.roles) {
//            if (!request.isUserInRole(role)) return null;
//        }
        if (this.roles.stream().anyMatch(role -> !request.isUserInRole(role))) {
            return null;
        }
        return this;
    }

    @Override
    public int compareTo(RolesRequestCondition other, HttpServletRequest request) {
        return other.roles.size() - this.roles.size();
    }
}
