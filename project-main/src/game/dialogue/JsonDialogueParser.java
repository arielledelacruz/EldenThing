package game.dialogue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses JSON strings representing dialogues into {@link Dialogue} objects.
 * <p>
 * The expected JSON format is an object containing two fields:
 * <ul>
 *     <li>"options": a nested array of strings representing player options per round</li>
 *     <li>"responses": a nested array of strings representing NPC responses per round</li>
 * </ul>
 * This parser converts those nested arrays into lists of lists of strings.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class JsonDialogueParser implements DialogueParser {

    /**
     * Parses a JSON string into a {@link Dialogue} object.
     *
     * @param jsonString the JSON string to parse
     * @return a Dialogue object representing the parsed data
     * @throws DialogueParsingException if parsing fails due to invalid JSON structure
     */
    @Override
    public Dialogue parse(String jsonString) throws DialogueParsingException {
        try {
            JSONObject root = new JSONObject(jsonString);


            // Parse nested arrays into List<List<String>>
            List<List<String>> options = parseNestedStringArray(root.getJSONArray("options"));
            List<List<String>> responses = parseNestedStringArray(root.getJSONArray("responses"));

            return new Dialogue(options, responses);

        } catch (JSONException e) {
            throw new DialogueParsingException("Failed to parse Dialogue JSON", e);
        }
    }

    /**
     * Helper method to convert a JSONArray of JSONArrays of strings into a List of Lists of strings.
     *
     * @param outer the outer JSONArray containing inner JSONArrays of strings
     * @return a List of Lists of strings extracted from the JSON structure
     */
    private List<List<String>> parseNestedStringArray(JSONArray outer) {
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < outer.length(); i++) {
            JSONArray inner = outer.getJSONArray(i);
            List<String> row = new ArrayList<>();
            for (int j = 0; j < inner.length(); j++) {
                row.add(inner.getString(j));
            }
            result.add(row);
        }

        return result;
    }
}
