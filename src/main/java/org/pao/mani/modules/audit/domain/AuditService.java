package org.pao.mani.modules.audit.domain;

import org.pao.mani.core.Timestamp;

public interface AuditService {
    void log(String action, Timestamp timestamp);
}
