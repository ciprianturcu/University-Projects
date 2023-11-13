import 'package:flutter/material.dart';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/game.dart';

class AddScreen extends StatefulWidget {
  @override
  State<AddScreen> createState() => _AddScreenState();
}

class _AddScreenState extends State<AddScreen> {
  late DatabaseHelper databaseHelper;
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();
  final TextEditingController _genreController = TextEditingController();
  final TextEditingController _progressController = TextEditingController();
  final TextEditingController _ratingController = TextEditingController();
  final TextEditingController _hoursPlayedController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
    databaseHelper = DatabaseHelper();
    databaseHelper.databaseInit().whenComplete(() async {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text(
          'Add Game',
          style: TextStyle(fontWeight: FontWeight.w700, fontSize: 34.0),
        ),
        backgroundColor: const Color(0xFF212223),
      ),
      backgroundColor: const Color(0xFF212223),
      body: Padding(
          padding: const EdgeInsets.all(16),
          child: SingleChildScrollView(
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  TextFormField(
                    controller: _titleController,
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Title',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a title';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    controller: _descriptionController,
                    decoration: const InputDecoration(
                        labelText: 'Description',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a description';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    controller: _genreController,
                    decoration: const InputDecoration(
                        labelText: 'Genre',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a genre';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    controller: _progressController,
                    decoration: const InputDecoration(
                        labelText: 'Progress',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the progress (between 0-100)';
                      }
                      if (double.tryParse(value)! < 0 ||
                          double.tryParse(value)! > 100) {
                        return 'Not in the range of (0-100)';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    controller: _ratingController,
                    decoration: const InputDecoration(
                        labelText: 'Rating',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the rating (between 0-5)';
                      }
                      if (double.tryParse(value)! < 0 ||
                          double.tryParse(value)! > 5) {
                        return 'Not in the range of (0-5)';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    controller: _hoursPlayedController,
                    decoration: const InputDecoration(
                        labelText: 'Hours Played',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the number of hours played';
                      }
                      if (int.tryParse(value)! < 0) {
                        return 'Should be grater than 0';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF35363A),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          side: const BorderSide(color: Color(0xFFFFD232)),
                        ),
                      ),
                      onPressed: () {
                        if (_formKey.currentState!.validate()) {
                          String title = _titleController.text;
                          String description = _descriptionController.text;
                          String genre = _genreController.text;
                          double progress =
                              double.parse(_progressController.text);
                          double rating = double.parse(_ratingController.text);
                          int hoursPlayed =
                              int.parse(_hoursPlayedController.text);
                          Game game = Game(
                            title: title,
                            description: description,
                            genre: genre,
                            progress: progress,
                            rating: rating,
                            hoursPlayed: hoursPlayed,
                          );
                          _addGameToDatabase(game);
                        }
                      },
                      child: const Text(
                        'Add Game',
                        style: TextStyle(color: Color(0xFFFFD232)),
                      ))
                ],
              ),
            ),
          )),
    );
  }

  void _addGameToDatabase(Game game) async {
    int result = await databaseHelper.createGame(game);

    if (result != 0) {
      _showDialog(true);
    } else {
      _showDialog(false);
    }
  }

  void _showDialog(bool isSuccess) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          backgroundColor: const Color(0xFF333437),
          title: Text(isSuccess ? 'Success' : 'Error', style: const TextStyle(color: Color(0xFFE0E0E2)),),
          content: Text(
            isSuccess
                ? 'Game added successfully!'
                : 'Failed to add the game. Please try again.',
            style: const TextStyle(color: Color(0xFFE0E0E2)),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close the dialog
                if (isSuccess) {
                  Navigator.pop(context); // Go back to the previous screen
                }
              },
              style: TextButton.styleFrom(
                backgroundColor: const Color(0xFFFFD232),
              ),
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }
}
