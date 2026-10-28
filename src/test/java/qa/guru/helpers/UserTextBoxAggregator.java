package qa.guru.helpers;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import qa.guru.data.UserTextBoxRecord;

public class UserTextBoxAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) {
        return new UserTextBoxRecord(
                accessor.getString(0) == null ? "" : accessor.getString(0),
                accessor.getString(1) == null ? "" : accessor.getString(1),
                accessor.getString(2) == null ? "" : accessor.getString(2),
                accessor.getString(3) == null ? "" : accessor.getString(3)
        );
    }
}
