package my.finance.hackathon.app.dto.operation;

import my.finance.hackathon.app.model.OperationType;

public interface UpsertOperationRequest {

    OperationType getType();

}
