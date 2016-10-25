package electrostatic.java.fx.model;

import java.text.DecimalFormat;
import javafx.util.StringConverter;

public class Converter {
	
	private static DecimalFormat formatter = new DecimalFormat("#0");
//	private static DecimalFormat formatterForce = new DecimalFormat("#0.00");
	
	static public StringConverter<Number> yInverseConverter(double height){
		
		return new StringConverter<Number>() {
			@Override
			public String toString(Number object) {
				double d = height - object.doubleValue();

				return object == null ? "" : formatter.format(d);
			}
			@Override
			public Number fromString(String string) {
				double d = Double.valueOf(string);
				d = height - d;

				return (string != null && !string.isEmpty()) ? d : null;
			}
		};
	}
	
	
	

	static StringConverter<Number> yForceInverseConverter = new StringConverter<Number>() {
		@Override
		public String toString(Number object) {
			double d = -object.doubleValue();
			if(d == 0)
				return "0";
			else
				return 	formatter.format(d);
		
		}

		@Override
		public Number fromString(String string) {
			double d = Double.valueOf(string);

			return (string != null && !string.isEmpty()) ? d : null;
		}
	};
	
	static StringConverter<Number> normalConverter = new StringConverter<Number>() {
		@Override
		public String toString(Number object) {
			double d = object.doubleValue();
			if(d == 0)
				return "0";
			else
				return object == null ? "" : formatter.format(d);
		}

		@Override
		public Number fromString(String string) {
			double d = Double.valueOf(string);

			return (string != null && !string.isEmpty()) ? d : null;
		}
	};
	
	static public  String mouseYConverter(double value, double width){
		
		return String.valueOf(width - value);
	}
	
	public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
	
}
