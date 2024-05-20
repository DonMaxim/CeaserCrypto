public class Constants {
   private static final String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
   private static final String eng = "abcdefghijklmnopqrstuvwxyz";
   private static final String cypher = "0123456789";
   private static final String symbols = " .,<>/;:'\"!@#$%^&*()-_[]{}`~№?=+";
   public static final String ALPHABET = rus + eng + rus.toUpperCase() + eng.toUpperCase() + cypher + symbols;
}
