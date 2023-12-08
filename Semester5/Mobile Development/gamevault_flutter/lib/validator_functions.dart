class Validator {

  static String? validateStringFieldInput(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter a title';
    }
    return null;
  }

  static String? validateRatingInput(String? value) {
    if (value == null || double.tryParse(value) == null) {
      return 'Please enter the rating (between 0-5)';
    }
    if (double.tryParse(value)! < 0 || double.tryParse(value)! > 5) {
      return 'Not in the range of (0-5)';
    }
    return null;
  }

  static String? validateHoursPlayedInput(String? value) {
    if (value == null || double.tryParse(value) == null) {
      return 'Please enter the number of hours played';
    }
    if (int.tryParse(value)! < 0) {
      return 'Should be greater than 0';
    }
    return null;
  }

  static String? validateProgressInput(String? value) {
    if (value == null || double.tryParse(value) == null) {
      return 'Please enter the progress (between 0-100)';
    }
    if (double.tryParse(value)! < 0 || double.tryParse(value)! > 100) {
      return 'Not in the range of (0-100)';
    }
    return null;
  }
}
