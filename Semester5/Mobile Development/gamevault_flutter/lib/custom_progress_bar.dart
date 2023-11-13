import 'package:flutter/material.dart';

class CustomProgressBar extends StatelessWidget {
  final Color backgroundColor;
  final Gradient foregroundGradient;
  final double percent;
  final bool isShownText;

  CustomProgressBar({
    required this.backgroundColor,
    required this.foregroundGradient,
    required this.percent,
    required this.isShownText,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 20, // Set your desired height
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.circular(10),
      ),
      child: Stack(
        children: [
          FractionallySizedBox(
            widthFactor: percent / 100,
            child: Container(
              decoration: BoxDecoration(
                gradient: foregroundGradient,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
          ),
          if (isShownText)
            Center(
              child: Text(
                "${percent.toStringAsFixed(1)} %",
                style: TextStyle(color: Colors.white),
              ),
            ),
        ],
      ),
    );
  }
}
