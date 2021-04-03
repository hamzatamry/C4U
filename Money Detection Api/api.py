import os
from flask import Flask, flash, request, redirect, url_for
from werkzeug.utils import secure_filename
from flask import send_from_directory
from C4U import getMoneyValue


UPLOAD_FOLDER = './Uploads'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
# app.config["DEBUG"] = True

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        if len(request.files)==0:
            return "Empty request body"
        # check if the post request has the file part
        if 'file' not in request.files:
            flash('No file part')
            return "Request not OK"
        file = request.files['file']
        # if user does not select file, browser also
        # submit an empty part without filename
        if file.filename == '':
            flash('No selected file')
            return "No file uploaded"
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            return getMoneyValue(os.path.join(app.config['UPLOAD_FOLDER'], filename))
    else:
    	return "Method not supported"
app.run(host="0.0.0.0", port=5000, debug=True)