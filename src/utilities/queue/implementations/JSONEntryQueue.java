package utilities.queue.implementations;

import utilities.json.JSONEntry;
import utilities.queue.QueueNode;
import utilities.queue.QueueSL;

public class JSONEntryQueue extends QueueSL<JSONEntry>{
    @Override
    protected QueueNode<JSONEntry> createNewNode(JSONEntry e) {
        return new SimpleQueueNode<JSONEntry>(e);
    }
}
