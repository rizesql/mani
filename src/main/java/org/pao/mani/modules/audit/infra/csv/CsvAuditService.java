package org.pao.mani.modules.audit.infra.csv;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.audit.domain.AuditService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CsvAuditService implements AuditService {
    private static final String LOG_FILE_NAME = "audit-log";
    private static final UUID instanceId = UUID.randomUUID();

    public final String logfilePath = LOG_FILE_NAME + "-" + instanceId + ".csv";


    public CsvAuditService() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logfilePath, true))) {
            if (new java.io.File(logfilePath).length() == 0) {
                writer.println("Action,Timestamp");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize audit log file", e);
        }
    }

    @Override
    public synchronized void log(String action, Timestamp timestamp) {
        try (var pw = new PrintWriter(new FileWriter(logfilePath, true))) {
            var entry = escape(action) + "," + escape(timestamp.toInstant().toString());
            pw.println(entry);
        } catch (IOException e) {
            throw new RuntimeException("Could not log audit event", e);
        }
    }

    private String escape(String data) {
        Objects.requireNonNull(data, "data cannot be null");

        var escapedData = data.replaceAll("\\R", " ");
        if (escapedData.contains(",") || escapedData.contains("\"") || escapedData.contains("'")) {
            escapedData = escapedData.replace("\"", "\"\"");
            escapedData = "\"" + escapedData + "\"";
        }
        return escapedData;
    }
}
