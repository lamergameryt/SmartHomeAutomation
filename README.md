## Smart Home Automation using Facial Recognition

Project created by students of Vishwakarma Institute of Technology IT-A Group 15 @ 2023.
You can use this project by referring to the [installation guide](#installation) of the project.

### Prerequisites

This project depends on a few prerequisites for the execution of the project.

This project uses the following tools. Make sure the following dependencies are installed and configured:

- Java (OpenJDK 17.0.8)
- Maven (3.9.4)
- Python (3.10.12)
- [face-recognition](#face-recognition-installation-guide) library (>= 1.3.0)

### Installation

Before installation ensure you meet all the [required prerequisites](#prerequisites) for the project.

- Clone the repository to your local machine and cd into it.
- Copy or rename the `mysql.json.example` file to `mysql.json` in the resources folder at `src/main/resources`
- Modify the setting present in the `mysql.json` file and save the changes.
- Build the project by using `mvn clean package` and cd into the target directory.
- Run the built project by executing `java -jar target/fdwebview-VERSION.jar`

#### Face Recognition Installation Guide

To install the face recognition library, follow the below steps:

- Ensure you have [Microsoft Visual C++ Build Tools](https://visualstudio.microsoft.com/downloads/) installed.
- For GPU Acceleration, download & install [CUDA](https://developer.nvidia.com/cuda-downloads) and [cuDNN](https://developer.nvidia.com/cudnn).
  Without these, the face recognition library will run on CPU and won't perform efficiently. _(Optional)_
- Download and build [dlib](https://github.com/davisking/dlib) from source to ensure CUDA is enabled. _(Optional)_
- Install face_recognition using pip with the following command `pip install face-recognition`
- Success! Face recognition should be installed with all the models.
