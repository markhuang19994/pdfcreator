package analysis;

import java.util.*;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/29, MarkHuang,new
 * </ul>
 * @since 2018/10/29
 */
public class ActionAnalysis {
    private Map<String, List<String>> actionMap = new HashMap<>();

    private ActionAnalysis(String[] args) {
        for (String arg : args) {
            List<String> tempParams = new ArrayList<>();
            if (arg.charAt(0) == '-') {
                tempParams = new ArrayList<>();
                actionMap.put(arg, tempParams);
            } else {
                tempParams.add(arg);
            }
        }
    }

    public static ActionAnalysis getInstance(String[] args) {
        return new ActionAnalysis(args);
    }

    public Map<String, List<String>> getActionMap() {
        return actionMap;
    }

    public Optional<String> getActionFirstParam(String action) {
        if (!this.actionMap.containsKey(action)) return Optional.empty();
        List<String> params = this.actionMap.get(action);
        if (params == null || params.isEmpty()) return Optional.empty();
        return Optional.of(params.get(0));
    }
}
