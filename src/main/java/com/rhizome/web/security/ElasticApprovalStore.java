package com.rhizome.web.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Component;

import com.rhizome.services.api.OauthApprovalsService;

@Component
public class ElasticApprovalStore implements ApprovalStore{

    @Autowired
    private OauthApprovalsService approvalsService;

    @Override
    public boolean addApprovals(Collection<Approval> approvals) {
        return approvalsService.saveApprovals(approvals);
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        return approvalsService.revokeApprovals(approvals);
    }

    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        return approvalsService.getApprovals(userId, clientId);
    }

}
