package ca.utoronto.ece419.tester.wrapper;

import ca.utoronto.ece419.tester.wrapper.exceptions.EncounteredException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Wrapper {
    protected static void loadClassMethods(Class classObject, Set<String> captureMethods, Map<String, Method> methods) {
        Set<String> methodsLeft = new HashSet<>(captureMethods);

        Class classObjectPtr = classObject;
        while(classObjectPtr != Object.class && classObjectPtr != null) {
            System.out.println(classObjectPtr);

            for(Method method : classObjectPtr.getDeclaredMethods()) {
                if(methodsLeft.contains(method.getName())) {
                    methods.put(method.getName(), method);
                    methodsLeft.remove(method.getName());
                }
            }

            classObjectPtr = classObjectPtr.getSuperclass();
        }

        if(methodsLeft.size() > 0) {
            StringBuilder missingMethods = new StringBuilder();
            boolean first = true;
            for(String methodName : methodsLeft) {
                if(!first) {
                    missingMethods.append(", ");
                }else{
                    first = false;
                }
                missingMethods.append("'" + methodName + "'");
            }
            throw new EncounteredException("Missing some methods: " + missingMethods);
        }
    }
}
