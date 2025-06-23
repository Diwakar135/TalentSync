from flask import Blueprint, request, jsonify
from werkzeug.security import generate_password_hash, check_password_hash
from models.user import db, User

auth_routes = Blueprint('auth_routes', __name__)

@auth_routes.route('/auth/register', methods=['POST'])
def register_user():
    data = request.get_json()
    name = data.get('name')
    email = data.get('email')
    password = data.get('password')
    role = data.get('role')

    if not name or not email or not password or role not in ['candidate', 'recruiter']:
        return jsonify({'success': False, 'error': 'Missing fields'}), 400

    if User.query.filter_by(email=email).first():
        return jsonify({'success': False, 'error': 'User already exists'}), 409

    hashed_pw = generate_password_hash(password)

    user = User(name=name, email=email, password=hashed_pw, role=role)
    db.session.add(user)
    db.session.commit()

    return jsonify({'message': 'Registered successfully'})

@auth_routes.route('/auth/login', methods=['POST'])
def login_user():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')

    user = User.query.filter_by(email=email).first()
    if not user or not check_password_hash(user.password, password):
        return jsonify({'success': False, 'error': 'Invalid email or password'}), 401

    return jsonify({
        'success': True,
        'role': user.role,
        'name': user.name,
        'email': user.email
    })
