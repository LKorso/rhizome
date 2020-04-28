package com.rhizome.services.api;

import java.util.Collection;

import org.springframework.security.oauth2.provider.approval.Approval;

public interface OauthApprovalsService {

    boolean saveApprovals(Collection<Approval> approvals);

    boolean revokeApprovals(Collection<Approval> approvals);

    Collection<Approval> getApprovals(String userId, String clientId);

}
