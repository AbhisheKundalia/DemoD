package com.awiserk.kundalias.demo2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awiserk.kundalias.demo2.data.DataProvider;
import com.awiserk.kundalias.demo2.data.Item;
import com.awiserk.kundalias.demo2.utils.EventListenerForSanityCheck;
import com.awiserk.kundalias.demo2.utils.NumberTextWatcherForThousand;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditoryActivity extends AppCompatActivity implements IPickResult {
    //Error Code for each field
    final int ERROR_IMAGE = 0;
    final int ERROR_ID = 1;
    final int ERROR_PRICE = 2;
    final int ERROR_SIZES = 3;
    final boolean APPENDCATEGORY = true;
    final boolean[] initError = new boolean[]{false, false, false, false};
    final EventListenerForSanityCheck error = new EventListenerForSanityCheck(initError);
    int savecounter = 0;
    Uri mImageUri = null;
    /**
     * Card Image View to hold each Item
     */
    private CardView mItemCardView;
    /**
     * ImageView field to add the item Image
     */
    private ImageView mItemImageView;
    /**
     * TextView Label to add Image
     */
    private TextView mItemImagelabelTextView;
    /**
     * Linearlayout block field to enter the item Size Checkbox
     */
    //private LinearLayout mItemSizeCheckboxLinearLayout;
    /**
     * TextView Label to display Image Error
     */
    private TextView mItemImageErrorTextView;
    /**
     * EditText field to enter the item Id
     */
    private Spinner mItemCategorySpinner;
    /**
     * EditText field to enter the item Id
     */
    private EditText mItemIdEditText;
    /**
     * TextInputLayout to display Item ID error
     */
    private TextInputLayout mItemIdErrorTextInputLayout;
    /**
     * EditText field to enter the item Price
     */
    private EditText mItemPriceEditText;
    /**
     * TextInputLayout to display Item Price error
     */
    private TextInputLayout mItemPriceErrorTextInputLayout;
    /**
     * Linear Layout to display Item sizes
     */
    private LinearLayout mItemSizeLinearLayout;
    /**
     * TextView Label to display Image Error
     */
    private TextView mItemSizeErrorTextView;

    /**
     * Boolean flag that keeps track of whether the item has been edited (true) or not (false)
     */
    private boolean mItemHasChanged = false;
    private String mCategory;
    /**
     * CheckBox array to reference to all the checkboxes
     */
    private CheckBox cb[] = null;
    /**
     * Keep track of total number of checkboxes available
     */
    private int arrayLength = 0;


    CheckBox.OnCheckedChangeListener mCheckboxTouchListener = null;
    /**
     * stores all the available sizes based on user selection
     */
    private ArrayList<String> availableSizes = null;
    private Menu menu = null;
    private boolean isSaveMenuItemEnabled = true;
    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mItemHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mCategory = getString(R.string.category_unknown);

        // Find all relevant views that we will need to read user input from
        mItemCardView = (CardView) findViewById(R.id.card_image_view);
        mItemImageView = (ImageView) findViewById(R.id.select_imagebutton);
        mItemImagelabelTextView = (TextView) findViewById(R.id.add_image_text);
        mItemCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mItemIdEditText = (EditText) findViewById(R.id.input_id);
        mItemPriceEditText = (EditText) findViewById(R.id.input_price);
        mItemImageErrorTextView = (TextView) findViewById(R.id.image_error_view);
        mItemIdErrorTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_id);
        mItemPriceErrorTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_price);
        mItemSizeLinearLayout = (LinearLayout) findViewById(R.id.text_input_layout_sizes);
        mItemSizeErrorTextView = (TextView) findViewById(R.id.size_error_view);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mItemImageView.setOnTouchListener(mTouchListener);
        mItemCategorySpinner.setOnTouchListener(mTouchListener);
        mItemIdEditText.setOnTouchListener(mTouchListener);
        mItemPriceEditText.setOnTouchListener(mTouchListener);
        mItemPriceEditText.addTextChangedListener(new NumberTextWatcherForThousand(mItemPriceEditText));


        //Loads the already set image on device rotation
        if (savedInstanceState != null) {
            mImageUri = savedInstanceState.getParcelable("uri");
            if(mImageUri != null) {
                updateImage(mImageUri);
            }
            mItemHasChanged = savedInstanceState.getBoolean("itemChanged");
            isSaveMenuItemEnabled = savedInstanceState.getBoolean("isSaveMenuItemEnabled");
            savecounter = savedInstanceState.getInt("saveCounter", 0);
            //restore the checked check boxes
            availableSizes = savedInstanceState.getStringArrayList("checkedAvailabelSizes");
        }

        // ImagePickerButton shows an image picker to upload a image for a message
        mItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(EditoryActivity.this);
            }
        });


        setupSpinner();

    }

    // Saves the state of image selected on device rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isSaveMenuItemEnabled", isSaveMenuItemEnabled);
        outState.putInt("saveCounter", savecounter);
        // greater than 1 and not 0 as size will be one on additon of mcategory to the list as parammeter is set to true
        // mcategory is added to identify that sizes belong to which category on rotation
        if(getCheckedSizes(APPENDCATEGORY).size() > 1)
        {
            outState.putStringArrayList("checkedAvailabelSizes", getCheckedSizes(APPENDCATEGORY));
        }
        //elsa throws null pointer exception
        if (mImageUri != null)
            outState.putParcelable("uri", mImageUri);
        if (mItemHasChanged)
            outState.putBoolean("itemChanged", mItemHasChanged);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*// Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        mImageUri = savedInstanceState.getParcelable("uri");
        mItemHasChanged = savedInstanceState.getBoolean("itemChanged");
        isSaveMenuItemEnabled = savedInstanceState.getBoolean("isSaveMenuItemEnabled");
        savecounter = savedInstanceState.getInt("saveCounter");*/
        // To set listener on device rotation if Save button is already clicked
        //Log.i("*ONRESTOREiTEM*:", String.valueOf(savecounter));
        /*updateImage(mImageUri);*/
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);
            mImageUri = r.getUri();
            mItemHasChanged = true;
            //Setting the real returned image.
            updateImage(mImageUri);

            //If you want the Bitmap.
            //mItemImageView.setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * SetImage into the Image view by cleaning up the existing data
     */
    private void updateImage(Uri imageUri) {
        if (mItemHasChanged) {
            mItemCardView.setAlpha(1.0f);
            mItemImagelabelTextView.setVisibility(View.GONE);
            mItemImageView.setPadding(0, 0, 0, 0);
            mItemImageView.setImageURI(imageUri);
            //Check and update Error if image is not selected
            initError[ERROR_IMAGE] = !isImageSelected();
            error.setBoo(initError);
        }
    }

    /**
     * Setup the dropdown spinner that allows the user to select the Category of the Item.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mItemCategorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mItemCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category1))) {
                        mCategory = getString(R.string.category1);
                        addSizesCheckbox(DataProvider.ringsSizes);
                    } else if (selection.equals(getString(R.string.category2))) {
                        mCategory = getString(R.string.category2);
                        addSizesCheckbox(DataProvider.banglesSizes);
                    } else if (selection.equals(getString(R.string.category3))) {
                        mCategory = getString(R.string.category3);
                        addSizesCheckbox(DataProvider.chainsSizes);
                    } else if (selection.equals(getString(R.string.category4))) {
                        mCategory = getString(R.string.category4);
                        addSizesCheckbox(DataProvider.necklaceSizes);
                    } else {
                        mCategory = getString(R.string.category_unknown);
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = getString(R.string.category_unknown);
            }
        });
    }


    private void addSizesCheckbox(String[] sizes) {
        arrayLength = Array.getLength(sizes);
        mItemSizeLinearLayout.removeAllViewsInLayout();
        //if(mItemSizeCheckboxLinearLayout.getChildCount() > 0)
        //   mItemSizeCheckboxLinearLayout.removeAllViews();
        if (arrayLength > 0) {
            cb = new CheckBox[arrayLength];
            // J is se to 1 as index 0 holds the category name
            for (int i = 0, j = 1; i < arrayLength; i++) {
                cb[i] = new CheckBox(getApplicationContext());
                cb[i].setText(sizes[i]);
                mItemSizeLinearLayout.addView(cb[i]);
                mItemSizeLinearLayout.setOnTouchListener(mTouchListener);

                //restore the state before rotation
                //Check if available size is not null and its size is greater than 0 to compare and allow checked
                //checkboxes on screen rotation
                if(availableSizes != null && availableSizes.size() > 0 && availableSizes.get(0).equalsIgnoreCase(mCategory))
                {

                    if(j < availableSizes.size() && sizes[i].trim().equalsIgnoreCase(availableSizes.get(j).trim()) ) {
                        cb[i].setChecked(true);
                        j++;
                    }

                }else if(availableSizes != null)
                {
                    availableSizes.clear();
                }

                if(savecounter > 0)
                {
                    //Set onChecked listener to the checkboxes to get status
                    cb[i].setOnCheckedChangeListener(mCheckboxTouchListener);
                }
            }
            if(savecounter > 0)
            {                //Setting error to true as when initialized checkboxes will be unchecked thus show error
                initError[ERROR_SIZES] = !isCheckboxSelected();
                error.setBoo(initError);
            }
        }


    }

    /**
     * Method to enable menu item
     *
     * @param item
     */
    public void setEnabled(MenuItem item) {
        isSaveMenuItemEnabled = true;
        // Enabled
        item.setEnabled(true);
        item.getIcon().setAlpha(255);

    }

    /**
     * Method to disable menu item
     *
     * @param item
     */
    public void setDisabled(MenuItem item) {
        isSaveMenuItemEnabled = false;
        // Disabled
        item.setEnabled(false);
        item.getIcon().setAlpha(130);

    }


    /**
     * This method checks if Image is selected or not
     *
     * @return true if image is selected and false if image is not selected
     */
    public boolean isImageSelected() {
        //Check if image is selected else display error message
        if (mImageUri == null) {
            mItemImageErrorTextView.setText(getString(R.string.error_image));
            return false;
        } else {
            mItemImageErrorTextView.setText(null);
            return true;
        }

    }


    /**
     * This method Check if any of the checkbox is selected
     *
     * @return true if atleast single checkbox is selectd and false if no checkbox is selected
     */
    public boolean isCheckboxSelected() {
        //check if single checkbox is selected
        for (int i = 0; i < arrayLength; i++) {
            if (cb[i].isChecked()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method performs data sanity check on all the input fields and diplays error with listener
     * Returns the Item object created of clear data
     */
    public boolean sanityCheckInputData() {
        //removeSanityErrors();

        /**
         * OnTouchListener that listens for any user touches on a Checkbox, implying that they are modifying
         * the checkboxes, and we set the error accordingly
         */
        mCheckboxTouchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initError[ERROR_SIZES] = !isCheckboxSelected();
                error.setBoo(initError);
            }
        };

        //Set event listener for value to check if there is no error and enable save button
        error.setListener(new EventListenerForSanityCheck.ChangeListener() {
            @Override
            public void onError() {

                // disabled
                setDisabled(menu.findItem(R.id.action_save));
                Toast.makeText(EditoryActivity.this, "Save button disabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoError() {


                setEnabled(menu.findItem(R.id.action_save));
                Toast.makeText(EditoryActivity.this, "Save button enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCheckboxError(boolean isClean) {
                //Condition set so that error is not overwritten giving blinking effect to error
                if(mItemSizeErrorTextView.getText() == "" && initError[ERROR_SIZES])
                {
                    //Display error
                    mItemSizeErrorTextView.startAnimation(AnimationUtils.loadAnimation(EditoryActivity.this, android.R.anim.fade_in));
                    mItemSizeErrorTextView.setText(R.string.error_size);
                }
                else
                {
                    //Remove error
                    if(mItemSizeErrorTextView.getText() != "" && !initError[ERROR_SIZES])
                        mItemSizeErrorTextView.setText(null);
                }
            }
        });


        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String itemIdString = mItemIdEditText.getText().toString().trim();
        //Trims the visible commas of the price
        String itemPriceString = NumberTextWatcherForThousand.trimCommaOfString(mItemPriceEditText.getText().toString()).trim();


        // check if ID the field in the editor are blank and display error to enable/disable Save button
        if (TextUtils.isEmpty(itemIdString)) {   //update Sanity check Listener to disable Save menu item
            mItemIdErrorTextInputLayout.setError(getString(R.string.error_id));
            initError[ERROR_ID] = true;
            error.setBoo(initError);

        }

        //Listener to remove error if the user inputs some data
        mItemIdErrorTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    //update Sanity check Listener to disable Save menu item
                    initError[ERROR_ID] = true;
                    error.setBoo(initError);
                    mItemIdErrorTextInputLayout.setError(getString(R.string.error_id));
                }

                if (s.length() > 0 && mItemIdErrorTextInputLayout.getError() != null) {
                    //update Sanity check Listener to enable Save menu item
                    initError[ERROR_ID] = false;
                    error.setBoo(initError);
                    mItemIdErrorTextInputLayout.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        // check if the Price field in the editor are blank and display error to enable/disable Save button
        if (TextUtils.isEmpty(itemPriceString)) {   //update Sanity check Listener to disable Save menu item
            initError[ERROR_PRICE] = true;
            error.setBoo(initError);
            mItemPriceErrorTextInputLayout.setError(getString(R.string.error_price));
        }

        //Listener to remove error if the user inputs some data
        mItemPriceErrorTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    //update Sanity check Listener to disable Save menu item
                    initError[ERROR_PRICE] = true;
                    error.setBoo(initError);
                    mItemPriceErrorTextInputLayout.setError(getString(R.string.error_price));
                }

                if (s.length() > 0 && mItemPriceErrorTextInputLayout.getError() != null) {
                    //update Sanity check Listener to enable Save menu item
                    initError[ERROR_PRICE] = false;
                    error.setBoo(initError);
                    mItemPriceErrorTextInputLayout.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        //Check and update Sanity check Listener if image is not selected
        initError[ERROR_IMAGE] = !isImageSelected();
        error.setBoo(initError);

        for (int i = 0; i < arrayLength; i++)
            cb[i].setOnCheckedChangeListener(mCheckboxTouchListener);
        //Check and update Sanity check Listener if image is not selected
        initError[ERROR_SIZES] = !isCheckboxSelected();
        error.setBoo(initError);

        return EventListenerForSanityCheck.areAllFalse(initError);
    }



    /**
     * This method removes all the errors display as part of sanity check
     */
    public void removeSanityErrors() {
        //Remove error for all the fields
        mItemImageErrorTextView.setText(null);
        mItemIdErrorTextInputLayout.setError(null);
        mItemPriceErrorTextInputLayout.setError(null);
        mItemSizeErrorTextView.setText(null);
    }


    /**
     * Get the current checked available sizes
     * @param isCategoryAppended is true if category is required to be appended to restore the state on rotation
     *                           and is false if available sizes is required to write to database without category
     *                           appended and returning only checked sizes
     * @return StringArrayList of checked available sizes as per the input provided
     */
    private ArrayList<String> getCheckedSizes(boolean isCategoryAppended){
        this.availableSizes = new ArrayList<String>();
        if(isCategoryAppended)
        {
            this.availableSizes.add(mCategory);
        }
        //check if single checkbox is selected
        for(int i = 0; i < arrayLength; i++)
        {
            if(cb[i].isChecked())
            {
                this.availableSizes.add(cb[i].getText().toString());
            }
        }
        return this.availableSizes;//.toArray(new String[this.availableSizes.size()]);

    }


    /**
     * Set the Checked list as per input
     */
    private void restoreCheckboxState()
    {
        /*Log.i("available sizes:", String.valueOf(availableSizes));
        for(int i = 0, j = 0; i < arrayLength && j < availableSizes.length; i++)
        {
            Log.i("CB[i]:", cb[i].getText().toString());
            Log.i("available sizes[j]:", availableSizes[j]);
            if(cb[i].getText().toString() == availableSizes[j])
            {
                cb[i].setChecked(true);
                j++;
            }
        }*/
    }


    /**
     * Reads the current field values and Return an Item object with all values set
     */
    private Item getCurrentFieldValues()
    {
        String category = mCategory;
        // Use trim to eliminate leading or trailing white space
        String itemIdString = mItemIdEditText.getText().toString().trim();
        String imageUrl = mImageUri.toString();
        //Trims the visible commas of the price
        int itemPriceString = Integer.valueOf(NumberTextWatcherForThousand.trimCommaOfString(mItemPriceEditText.getText().toString()).trim());
        //Get checked sizes without appending category to write to database
        String[] availableSizes = getCheckedSizes(!APPENDCATEGORY).toArray(new String[this.availableSizes.size()]);

        return new Item(category,itemIdString,imageUrl,itemPriceString,availableSizes);
    }


    /**
     * Get user input from editor and save item into database.
     */
    private void saveItem() {

        removeSanityErrors();
        //Check error and attach listener if clicked on save for the first time (savecounter determines) as later listener
        //will enable and disable save button accordingly
        // Perform Sanity check on complete data and attach listener to display error
        if (savecounter == 0 && !sanityCheckInputData()) ;
        else {
            // Listener to perform action if user clicks on create/save on alert dialog
            DialogInterface.OnClickListener saveButtonClickListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // User clicked "Create" button, Write data to database and navigate to parent activity.
                            Toast.makeText(EditoryActivity.this, "Record create successfully", Toast.LENGTH_SHORT).show();
                            // Create a writable packet
                            Item item = getCurrentFieldValues();
                            NavUtils.navigateUpFromSameTask(EditoryActivity.this);
                        }
                    };

            // Show a dialog that notifies the user if they want to confirm the write
            showSaveConfirmDialog(saveButtonClickListener);
        }

        // Counter to trigger and maintain the state of application
        ++savecounter;

        //Check if the image is selected else display error message
        // and check if all the fields in the editor are blank and display error

        //TextInputLayout tilCategory = (TextInputLayout) findViewById(R.id.text_input_layout_category);
        // and check if all the fields in the editor are blank and display error
        //tilCategory.setError("This field can not be blank");
        // mItemSizeCheckboxLinearLayout.setError("select one check box atleast");


        /*if (TextUtils.isEmpty(imageURLString))
        {
            mItemImageView.
        }
                TextUtils.isEmpty(itemIdString) && mCategory == getString(R.string.category_unknown)) {

            if (itemPriceString.equalsIgnoreCase("")) {
                mItemPriceEditText.setError("This field can not be blank");
            }*/
        // Since no fields were modified, we can return early without creating a new pet.
        // No need to create ContentValues and no need to do any ContentProvider operations.



        /*// Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, nameString);
        values.put(PetEntry.COLUMN_PET_BREED, breedString);
        values.put(PetEntry.COLUMN_PET_GENDER, mGender);
        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int weight = 0;
        if (!TextUtils.isEmpty(weightString)) {
            weight = Integer.parseInt(weightString);
        }
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentPetUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentPetUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //To restore the alpha of the icon on recreating Menu Item from main activity
        menu.findItem(R.id.action_save).getIcon().setAlpha(255);
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;

        // If save Icon is alrady clicked once only then enable or disable icon as per flag
        if (savecounter > 0) {

            if (isSaveMenuItemEnabled) {
                setEnabled(menu.findItem(R.id.action_save));
            } else if (!isSaveMenuItemEnabled) {
                setDisabled(menu.findItem(R.id.action_save));
            }

            //Once the state is restored with above methods attach listenerr and restore the state of listener
            sanityCheckInputData();
        }

        // If this is a new item, hide the "Delete" menu item.
        /*if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save Item to database
                //TODO saveItem();
                saveItem();
                //setEnabled(item);
                // Exit activity
                //finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.


                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                if (mItemHasChanged) {
                    DialogInterface.OnClickListener discardButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User clicked "Discard" button, navigate to parent activity.
                                    NavUtils.navigateUpFromSameTask(EditoryActivity.this);
                                }
                            };

                    // Show a dialog that notifies the user they have unsaved changes
                    showUnsavedChangesDialog(discardButtonClickListener);
                    return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * Prompt the user to confirm that they want to delete this Item.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                //TODO deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the Item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Show a dialog that Confirms if the user is sure to Save the items
     * if they continue leaving the editor.
     *
     * @param SaveButtonClickListener is the click listener for what to do when
     *                                the user confirms they want to Save their changes
     */
    private void showSaveConfirmDialog(
            DialogInterface.OnClickListener SaveButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.save_and_create_dialog_msg);
        builder.setPositiveButton(R.string.create, SaveButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
