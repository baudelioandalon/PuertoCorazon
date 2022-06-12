import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

 /**
 * Synchronizing database emails and authentication emails for admins accounts.
 */
export const debugAdminAccounts = functions.firestore.document('/DEBUG/administrator@borealnetwork.org').onCreate((snap, context) => {
      const newUser = snap.data();
       admin
        .auth()
        .createUser({
          email: newUser.userData.email,
          password: 'Altemis132',
          uid: snap.id
        })
        .then(userRecord => {
            const currentDateString = Date.now();
            
          return admin.auth().setCustomUserClaims(userRecord.uid, {
            dateCreated: currentDateString,
            userType: 'Administrator'
          });
        })
        .catch(error => {
          console.log('Error creating user Administrator', error);
          return false;
        });
  });

  export const releaseAdminAccounts = functions.firestore.document('/RELEASE/administrator@borealnetwork.org').onCreate((snap, context) => {
    const newUser = snap.data();
     admin
      .auth()
      .createUser({
        email: newUser.userData.email,
        password: 'Altemis132',
        uid: snap.id
      })
      .then(userRecord => {
          const currentDateString = Date.now();
          
        return admin.auth().setCustomUserClaims(userRecord.uid, {
          dateCreated: currentDateString,
          userType: 'Administrator'
        });
      })
      .catch(error => {
        console.log('Error creating user Administrator', error);
        return false;
      });
});

  export const debugInstructorAccounts = functions.firestore.document('/DEBUG/administrator@borealnetwork.org/Instructors/{pushInstructor}').onCreate((snap, context) => {
    const newUser = snap.data();
     admin
      .auth()
      .createUser({
        email: newUser.lenderData.email,
        password: 'Altemis132',
        uid: snap.id
      })
      .then(userRecord => {
          const currentDateString = Date.now();
          
        return admin.auth().setCustomUserClaims(userRecord.uid, {
          dateCreated: currentDateString,
          userType: 'Instructor'
        });
      })
      .catch(error => {
        console.log('Error creating user Instructor', error);
        return false;
      });
});

export const releaseInstructorAccounts = functions.firestore.document('/RELEASE/administrator@borealnetwork.org/Instructors/{pushInstructor}').onCreate((snap, context) => {
  const newUser = snap.data();
   admin
    .auth()
    .createUser({
      email: newUser.lenderData.email,
      password: 'Altemis132',
      uid: snap.id
    })
    .then(userRecord => {
        const currentDateString = Date.now();
        
      return admin.auth().setCustomUserClaims(userRecord.uid, {
        dateCreated: currentDateString,
        userType: 'Instructor'
      });
    })
    .catch(error => {
      console.log('Error creating user Instructor', error);
      return false;
    });
});

export const debugClientAccounts = functions.firestore.document('/DEBUG/administrator@borealnetwork.org/Clients/{pushClient}').onCreate((snap, context) => {
  const newUser = snap.data();
   admin
    .auth()
    .createUser({
      email: newUser.clientData.email,
      password: 'Altemis132',
      uid: snap.id
    })
    .then(userRecord => {
        const currentDateString = Date.now();
        
      return admin.auth().setCustomUserClaims(userRecord.uid, {
        dateCreated: currentDateString,
        userType: 'Client'
      });
    })
    .catch(error => {
      console.log('Error creating user Client', error);
      return false;
    });
});

export const releaseClientAccounts = functions.firestore.document('/RELEASE/administrator@borealnetwork.org/Clients/{pushClient}').onCreate((snap, context) => {
  const newUser = snap.data();
   admin
    .auth()
    .createUser({
      email: newUser.clientData.email,
      password: 'Altemis132',
      uid: snap.id
    })
    .then(userRecord => {
        const currentDateString = Date.now();
        
      return admin.auth().setCustomUserClaims(userRecord.uid, {
        dateCreated: currentDateString,
        userType: 'Client'
      });
    })
    .catch(error => {
      console.log('Error creating user Client', error);
      return false;
    });
});

exports.debugCreateClientAccount = functions.auth.user().onCreate((user) => {
    const providerIds: String[] = []
    user.providerData.forEach((userData ) => {
        providerIds.push(userData.providerId)
    })
    if(!providerIds.includes('google.com')){
        return
    }
    admin.firestore().collection('DEBUG/administrator@borealnetwork.org/Clients').doc(user.uid).set({
        userData:{
            idClient: user.uid,
            providerId: providerIds[0],
            firstName: user.displayName || 'NONE',
            lastName: 'NONE',
            birthDay: 'NONE',
            email: user.email || 'NONE',
            phone: user.phoneNumber || 'NONE',
            typeUser: 'Client',
            sex: 'NONE',
            creationDate: admin.firestore.Timestamp.now(),
            imageUser: user.photoURL || 'NONE'
        }
    })
  });

  exports.releaseClientAccount = functions.auth.user().onCreate((user) => {
    const providerIds: String[] = []
    user.providerData.forEach((userData ) => {
        providerIds.push(userData.providerId)
    })
    if(!providerIds.includes('google.com')){
        return
    }
    admin.firestore().collection('RELEASE/administrator@borealnetwork.org/Clients').doc(user.uid).set({
        userData:{
            idClient: user.uid,
            providerId: providerIds[0],
            firstName: user.displayName || 'NONE',
            lastName: 'NONE',
            birthDay: 'NONE',
            email: user.email || 'NONE',
            phone: user.phoneNumber || 'NONE',
            typeUser: 'Client',
            sex: 'NONE',
            creationDate: admin.firestore.Timestamp.now(),
            imageUser: user.photoURL || 'NONE'
        }
    })
  });

  export const paymentTransaction = functions.https.onRequest((request: functions.https.Request, response: functions.Response<any>) => {
    const conekta = require('conekta');
    if(request.body.environmentLocal === 'DEBUG' || request.body.environmentLocal === 'DEBUG/'){
      conekta.api_key = 'key_etzcf2xbgvx5KrosSyVMcA';
    }else{
      conekta.api_key = 'key_GQiE8zGdAEVSBAfNKJVbLQ';
    }
    conekta.api_version = '2.0.0';
  
    
   // const idPayment = "idRandom(20)";
   // const countAdult = request.body.countAdult;
   // const countChild = request.body.countChild;
   // const idEvent = request.body.idEvent;
   // const namePackage = request.body.namePackage;
   // const isPackage = request.body.isPackage;

    const idClient = request.body.idClient;
    const nameUser = request.body.nameUser;
    const email = request.body.email;
    const amount = request.body.amount;
    const typeCard = request.body.typeCard;
    const lastFour = request.body.lastFour;
    const typePayment = request.body.typePayment;
    const phone =  request.body.phone
    const token = request.body.token;
    const saveCard = request.body.saveCard;
    const aliasCard = request.body.aliasCard;
    const expirationDate = request.body.expirationDate;
    const digitsCard = request.body.digitsCard;
    const emailLocal = request.body.emailLocal;
    const environmentLocal = request.body.environmentLocal;
    const packages = request.body.packages;

   
   // const payedDate = admin.firestore.Timestamp.now()
   // const idPayment = "idRandom(20)";
   // const countAdult = request.body.countAdult;
   // const countChild = request.body.countChild;
   // const idEvent = request.body.idEvent;
   // const namePackage = request.body.namePackage;
   // const isPackage = request.body.isPackage;
  
   for(let packageItem of packages){
     if(!packageItem.namePackage || !packageItem.idEvent
        || (!packageItem.countAdult && !packageItem.countChild)){
        return response.status(400).send({
                  code:400,
                  message: ["Faltan parametros en el body"]
              });
        }
   }

    if ( !idClient ||  !amount || !lastFour || !typeCard || !email || 
        !typePayment ) {
      return response.status(400).send({
          code:400,
          message: ["Faltan parametros en el body"]
      });
    }
    
    return conekta.Order.create({
      "line_items": [{
          "name": "Puerto Corazon compra",
          "quantity": 1,
          "unit_price": amount + '00'
      }],
      "currency": "MXN",
      "customer_info": {
        'name': nameUser,
        'email': email,
        'phone': phone
      },
      "metadata": { "description": 'Pago Puerto Corazon'},
      "charges":[{
        "payment_method": {
          'type': typePayment.toLowerCase(),
          'token_id': token
        }
      }]
    }, async(error: any, res:any) => {
        if (error) {
          console.error(error);
          return response.status(400).send({
              code: 400,
              message: error?.details.map( ( detail: any) => detail.message )
          });
        }
        //PASO 1 Validar guardado de tarjeta
        //PASO 2 REFLEJARLO EN LA RAMA Payments
        //PASO 3 REFLEJARLO EN LA RAMA Tickets
        let payedDate = admin.firestore.Timestamp.now()

        if(saveCard === true){
          const setCard = admin.firestore().collection(environmentLocal + '/' + emailLocal + '/Cards').doc()
          const cardId = setCard.id
          await setCard.set({
                  cardId: cardId,
                  idClient: idClient,
                  lastFourDigits: lastFour,
                  digitsCard: digitsCard,
                  expirationDate: expirationDate,
                  typeCard: typeCard,
                  aliasCard: aliasCard,
                  ownerName: nameUser,
                  creationDate: payedDate,
                  typePayment: typePayment
          })
        }
        

        const setPayment = admin.firestore().collection(environmentLocal + '/' + emailLocal + '/Payments').doc()
        const paymentId = setPayment.id
        await setPayment.set({
                amountPayed: amount,
                idClient: idClient,
                idPayment: paymentId,
                lastFourDigits: lastFour,
                payedDate: payedDate,
                typeCard: typeCard,
                aliasCard: aliasCard,
                ownerName: nameUser,
                typePayment: typePayment
        })

        //Situacion 1 => 2 paquetes
        //Situacion 2 => 2 Boletos individuales adulto
        //Situcaion 3 => 2 Boletos individuales niño
        //Situacion 4 => 1 Boleto Adulto ó 1 Boleto niño

        for(let packageItem of packages){
            
            const setTicket = admin.firestore().collection(environmentLocal + '/' + emailLocal + '/Tickets').doc()
                const idTicket = setTicket.id
                await setTicket.set({
                    attendedTime: [],
                    payedDate: payedDate,
                    countAdult: packageItem.countAdult,
                    countChild: packageItem.countChild,
                    priceItem: packageItem.priceItem,
                    nameEvent: packageItem.nameEvent,
                    imageEvent: packageItem.imageEvent,
                    idPackage: packageItem.idPackage,
                    idClient: idClient,
                    idPayment: paymentId,
                    idTicket: idTicket,
                    idEvent: packageItem.idEvent,
                    namePackage: packageItem.namePackage,
                    isPackage: packageItem.isPackage 
                })
        }
   
        return response.status(200).send({
            code: 200,
            message: ["Transaccion exitosa"]
        });
    });
  });

  