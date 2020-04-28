package com.rhizome.services.implementation;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;

import com.rhizome.domain.oauth.OauthApprovals;
import com.rhizome.repositories.oauth.OauthApprovalsRepository;
import com.rhizome.services.api.OauthApprovalsService;
import com.rhizome.services.mappers.OauthApprovalsMapper;

@Service
public class ElasticOauthApprovalsService implements OauthApprovalsService {

    @Autowired
    private OauthApprovalsRepository approvalsRepository;

    @Autowired
    private OauthApprovalsMapper oauthApprovalsMapper;

    @Override
    public boolean saveApprovals(Collection<Approval> approvals) {
        if (isNull(approvals) || approvals.isEmpty()) {
            return false;
        }
        approvalsRepository.save(toEntities(approvals));
        return true;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        if (isNull(approvals) || approvals.isEmpty()) {
            return false;
        }
        approvalsRepository.delete(toEntities(approvals));
        return true;
    }

    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        return toApprovals(approvalsRepository.findByUserIdAndClientId(userId, clientId));
    }

    private Collection<Approval> toApprovals(List<OauthApprovals> oauthApprovals) {
        return oauthApprovals.stream().map(v -> oauthApprovalsMapper.toApproval(v)).collect(toList());
    }

    private List<OauthApprovals> toEntities(Collection<Approval> approvals) {
        return approvals.stream().map(v -> oauthApprovalsMapper.toEntity(v)).collect(toList());
    }

}
