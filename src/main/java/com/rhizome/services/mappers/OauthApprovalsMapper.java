package com.rhizome.services.mappers;

import org.mapstruct.Mapper;
import org.springframework.security.oauth2.provider.approval.Approval;

import com.rhizome.domain.oauth.OauthApprovals;

@Mapper(componentModel = "spring")
public interface OauthApprovalsMapper {

    OauthApprovals toEntity(Approval source);

    default Approval toApproval(OauthApprovals source) {
        return new Approval(
                source.getUserId(),
                source.getClientId(),
                source.getScope(),
                source.getExpiresAt(),
                Approval.ApprovalStatus.valueOf(source.getStatus()),
                source.getLastUpdatedAt());
    }

}
