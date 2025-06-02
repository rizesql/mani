package org.pao.mani.modules.audit.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.audit.domain.Audit;
import org.pao.mani.modules.audit.domain.AuditService;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {
    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Before("@annotation(auditAnnotation)")
    public void auditMethodCall(JoinPoint joinPoint, Audit auditAnnotation) {
        var action = auditAnnotation.value();

        auditService.log(action, Timestamp.now());
    }
}
