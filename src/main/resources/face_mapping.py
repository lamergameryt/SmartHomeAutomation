import face_recognition
import sys

if len(sys.argv) < 2:
    print("Error! Invalid number of arguments passed.")
    print("Usage: python file.py image.jpg")
    sys.exit(1)

image_file = face_recognition.load_image_file(sys.argv[1])
print(face_recognition.api.face_encodings(image_file, model="small")[0].tobytes().hex())
