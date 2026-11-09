# AI Learning Course

A hands-on course for learning artificial intelligence and machine learning, from the fundamentals to building and deploying real models.

## Overview

This repository holds the course material, exercises, and projects. Each module pairs concepts with runnable code so you learn by doing rather than just reading.

## What You'll Learn

- Core concepts: what AI, machine learning, and deep learning actually mean
- Data handling: cleaning, exploring, and preparing datasets
- Classic ML: regression, classification, clustering, and evaluation
- Deep learning: neural networks, training loops, and tuning
- Modern AI: transformers, embeddings, and working with large language models
- Deployment: packaging and serving a model in a real application

## Repository Structure

```
AILearningCourse/
├── modules/        # Lesson material, one folder per module
├── notebooks/      # Jupyter notebooks for exploration and demos
├── datasets/       # Sample data used by exercises
├── projects/       # End-to-end project work
└── README.md
```

> Folders are created as the course is built out. Update this tree as new material is added.

## Getting Started

1. Clone the repository.
2. Create and activate a virtual environment.
3. Install the dependencies.

```bash
git clone <repository-url>
cd AILearningCourse
python -m venv .venv
# Windows
.venv\Scripts\activate
# macOS / Linux
source .venv/bin/activate
pip install -r requirements.txt
```

4. Launch Jupyter to work through the notebooks:

```bash
jupyter lab
```

## Prerequisites

- Python 3.10+
- Basic Python programming
- Comfort with high-school level math (a refresher is included where needed)
- No prior machine learning experience required

## Suggested Learning Path

Work through the modules in order. Each one builds on the previous:

| Module | Topic | Focus |
| ------ | ----- | ----- |
| 01 | Foundations | What AI/ML is, key terminology |
| 02 | Data | Loading, cleaning, and visualizing data |
| 03 | Classic ML | Regression and classification |
| 04 | Deep Learning | Neural networks and training |
| 05 | Modern AI | Transformers and LLM applications |
| 06 | Deployment | Serving a model end to end |

## Resources

- Course notes live alongside each module.
- External references and papers are linked inside the relevant lessons.

## Contributing

Found a mistake or want to add a lesson? Open an issue or submit a pull request.

## License

Add a license before distributing this material. [MIT](https://choosealicense.com/licenses/mit/) is a common choice for course content.
