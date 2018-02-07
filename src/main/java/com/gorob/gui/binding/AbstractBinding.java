package com.gorob.gui.binding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractBinding implements IBinding {
	protected Method getSetterMethod(Object model, String property) {
	    Method method = pruefeSetterMethodVorhanden(model.getClass(), property);

	    if (method!=null) {
	    	return method;
	    }

		throw new RuntimeException("SetterMethod '" + property + "' doesn't exist in model '" + model.getClass().getSimpleName() + "'!");
	}

	protected Method getGetterMethod(Object model, String property) {
		return getGetterMethod(model, property, 0);
	}

	protected Method getGetterMethod(Object model, String property, int anzahlExpectedParameter) {
	    Method method = pruefeGetterMethodVorhanden(model.getClass(), property, anzahlExpectedParameter);

	    if (method!=null) {
	    	return method;
	    }
	    
		throw new RuntimeException("GetterMethod '" + property + "' doesn't exist in model '" + model.getClass().getSimpleName() + "'!");
	}

	protected Method getMethod(Object model, String property, int anzahlExpectedParameter) {
	    Method method = pruefeGetterMethodVorhanden(model.getClass(), property, anzahlExpectedParameter);

	    if (method!=null) {
	    	return method;
	    }

		throw new RuntimeException("GetterMethod '" + property + "' doesn't exist in model '" + model.getClass().getSimpleName() + "'!");
	}
	
	@SuppressWarnings("rawtypes")
	private Method pruefeGetterMethodVorhanden(Class classObj, String property, int anzahlExpectedParameter) {
		Class classAkt = classObj;
		
		do {
			Method method = pruefeGetterMethodDirektVorhanden(classAkt, property, anzahlExpectedParameter);
			if (method!=null) {
				return method;
			}
			
			classAkt = classObj.getSuperclass();
		} while (classAkt!=null);
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	private Method pruefeSetterMethodVorhanden(Class classObj, String property) {
		Class classAkt = classObj;
		
		do {
			Method method = pruefeSetterMethodDirektVorhanden(classAkt, property);
			if (method!=null) {
				return method;
			}
			
			classAkt = classObj.getSuperclass();
		} while (classAkt!=null);
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	private Method pruefeGetterMethodDirektVorhanden(Class classObj, String property, int anzahlExpectedParameter) {
		String getterName = "get" + property.substring(0,1).toUpperCase() + property.substring(1);
		String getterName2 = "is" + property.substring(0,1).toUpperCase() + property.substring(1);
	
		Method method = pruefeMethodeDirektVorhanden(classObj, getterName, anzahlExpectedParameter);
		if (method!=null) { return method; }
		
		method = pruefeMethodeDirektVorhanden(classObj, getterName2, anzahlExpectedParameter);
		if (method!=null) { return method; }
		
		return null;
	}

	
	@SuppressWarnings("rawtypes")
	private Method pruefeSetterMethodDirektVorhanden(Class classObj, String property) {
		String setterName = "set" + property.substring(0,1).toUpperCase() + property.substring(1);
		return pruefeMethodeDirektVorhanden(classObj, setterName, 1);
	}
	
	@SuppressWarnings("rawtypes")
	private Method pruefeMethodeDirektVorhanden(Class classObj, String methodName, int anzahlExpectedParameter) {
		Method[] declaredMethods = classObj.getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (method.getName().equals(methodName) && method.getParameterTypes().length==anzahlExpectedParameter) {
				return method;
			}
		}
		return null;
	}
	
	protected Object getModelValue(Object model, String modelProperty) {
		return getModelValue(model, modelProperty, null);
	}

	protected Object getModelValue(Object model, String modelProperty, Integer index) {
        Method getterMethod = getGetterMethod(model, modelProperty, index==null ? 0 : 1);
		
		Object value = null;
		try {
			value = index==null ? getterMethod.invoke(model) : getterMethod.invoke(model, index);
		} catch (IllegalArgumentException e1) {
		} catch (SecurityException e1) {
		} catch (IllegalAccessException e1) {
		} catch (InvocationTargetException e1) {
		}
		
		return value;
	}

	protected void setModelValue(Object model, String modelProperty, Object valueToSet) {
        Method setterMethod = getSetterMethod(model, modelProperty);
		
		try {
			setterMethod.invoke(model, valueToSet);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	protected String getModelGetterValueType(Object model, String modelProperty) {
		return getSetterMethod(model, modelProperty).getParameterTypes()[0].getSimpleName();
	}


}
