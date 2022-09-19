package http.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Session {
    private UUID sessionId;
    private ZonedDateTime startDate;

    public Session(UUID sessionId) {
        this.sessionId = sessionId;
        this.startDate = ZonedDateTime.now();
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }
}
